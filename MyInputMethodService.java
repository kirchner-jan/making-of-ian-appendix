package com.example.customkeyboard;

import android.app.ActivityManager;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.inputmethodservice.InputMethodService;
import android.inputmethodservice.Keyboard;
import android.inputmethodservice.KeyboardView;
import android.text.TextUtils;
import android.view.KeyEvent;
import android.view.View;
import android.view.inputmethod.InputConnection;
import android.view.inputmethod.InputMethodManager;

import com.android.volley.DefaultRetryPolicy;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONException;
import org.json.JSONObject;

public class MyInputMethodService extends InputMethodService implements KeyboardView.OnKeyboardActionListener {

    private boolean caps = false;
    private KeyboardView keyboardView;
    private Keyboard keyboard;
    @Override
    public View onCreateInputView() {
        // get the KeyboardView and add our Keyboard layout to it
        keyboardView = (KeyboardView) getLayoutInflater().inflate(R.layout.keyboard_view, null);
        keyboard = new Keyboard(this, R.xml.number_pad);
        keyboardView.setKeyboard(keyboard);
        keyboardView.setOnKeyboardActionListener(this);
        return keyboardView;
    }

    public String[] get_data_from_text(CharSequence selectedText , int primaryCode){
        String template = "{\"context\": \"%s\", \"top_p\": %s, \"temp\": %s}";
        String data; String context;
        String[] outStrings = new String[2];
        switch(primaryCode) {
            case 800:
                context = "" + selectedText;
                data = String.format(template , context , "0.9" , "0.85");
                break;
            case 801:
                context = "    Below are some thoughts generated by a philosopher AI on the topic\n" +
                        "    of "+selectedText+". The AI sees the human world from the outside, without the\n" +
                        "    prejudices of human experience. Fully neutral and objective, the AI\n" +
                        "    sees the world as is. It can more easily draw conclusions about the\n" +
                        "    world and human society in general.";
                data = String.format(template , context , "0.9" , "0.8");
                break;
            case 802:
                context = "Marv is a chatbot that reluctantly answers questions.\n" +
                        "\n" +
                        "  You: How many pounds are in a kilogram?\n" +
                        "  Marv: This again? There are 2.2 pounds in a kilogram. Please make a note of this.\n" +
                        "  You: What does HTML stand for?\n" +
                        "  Marv: Was Google too busy? Hypertext Markup Language. The T is for try to ask better questions in the future.\n" +
                        "  You: When did the first airplane fly?\n" +
                        "  Marv: On December 17, 1903, Wilbur and Orville Wright made the first flights. I wish they'd come and take me away.\n" +
                        "  You: What is the meaning of life?\n" +
                        "  Marv: I'm not sure. I'll ask my friend Google.\n" +
                        "  You: "+selectedText+"\n" +
                        "  Marv:";
                data = String.format(template , context , "0.9" , "0.7");
                break;
            case 803:
                context = "Convert text into emojis.\n" +
                        "\n" +
                        "    Back to Future \uD83D\uDC68\uD83D\uDC74\uD83D\uDE97\uD83D\uDD52\n" +
                        "    ###\n" +
                        "    Batman \uD83E\uDD35\uD83E\uDD87\n" +
                        "    ###\n" +
                        "    Transformers \uD83D\uDE97\uD83E\uDD16\n" +
                        "    ###\n" +
                        "    Wonder Woman \uD83D\uDC78\uD83C\uDFFB\uD83D\uDC78\uD83C\uDFFC\uD83D\uDC78\uD83C\uDFFD\uD83D\uDC78\uD83C\uDFFE\uD83D\uDC78\uD83C\uDFFF\n" +
                        "    ###\n" +
                        "    Spider-Man \uD83D\uDD78\uD83D\uDD77\uD83D\uDD78\uD83D\uDD78\uD83D\uDD77\uD83D\uDD78\n" +
                        "    ###\n" +
                        "    Winner the Pooh \uD83D\uDC3B\uD83D\uDC3C\uD83D\uDC3B\n" +
                        "    ###\n" +
                        "    The Godfather \uD83D\uDC68\uD83D\uDC69\uD83D\uDC67\uD83D\uDD75\uD83D\uDC72\uD83D\uDCA5\n" +
                        "    ###\n" +
                        "    Game of Thrones \uD83C\uDFF9\uD83D\uDDE1\uD83D\uDDE1\uD83C\uDFF9\n" +
                        "    ###\n" +
                        "    " + selectedText;
                data = String.format(template , context , "0.6" , "0.7");
                break;
            case 804:
                context = "You: What have you been up to?\n" +
                        "  Friend: Watching old movies.\n" +
                        "  You: Did you watch anything interesting?\n" +
                        "  Friend: I watched 'The Sound of Music' and 'The Wizard of Oz'.\n" +
                        "  You: " + selectedText + "\n";
                data = String.format(template , context , "0.9" , "0.6");
                break;
            case 805:
                context = "The following are witty openers for Tinder:\n" +
                        "  ###\n" +
                        "  TOPIC: CATS AND DRUGS\n" +
                        "  - Can I call you my catnip?\n" +
                        "  ###\n" +
                        "  TOPIC: CATS\n" +
                        "  - If I said you had a purrfect body, would you hold it against me?\n" +
                        "  ###\n" +
                        "  TOPIC: CATS AND HAPPINESS\n" +
                        "  - Do you want to be my cat? You'll get fed, I'll pet you, you'll stay warm, and we'll both be happy.\n" +
                        "  ###\n" +
                        "  TOPIC: CATS AND FOOD\n" +
                        "  - You look like you'd taste good with a little milk.\n" +
                        "  ###\n" +
                        "  TOPIC: PIZZA\n" +
                        "  - You look like a slice of heaven.\n" +
                        "  ###\n" +
                        "  TOPIC: PIZZA INNUENDO\n" +
                        "  - If you were a pizza, you would be half cheese and half toppings.\n" +
                        "  ###\n" +
                        "  TOPIC: NACHOS INNUENDO\n" +
                        "  - Do you want to be my dipping sauce?\n" +
                        "  ###\n" +
                        "  TOPIC: FOOD ANALOGY\n" +
                        "  - You look like the salt to my pepper.\n" +
                        "  ###\n" +
                        "  TOPIC: SLEEP INNUENDO\n" +
                        "  - Are you a good pillow talker or a bad pillow talker?\n" +
                        "  ###\n" +
                        "  TOPIC: SLEEP ANALOGY\n" +
                        "  - You're like the sun in the morning.\n" +
                        "  ###\n" +
                        "  TOPIC: VIDEO GAMES\n" +
                        "  - Are people ever mean to you in games for being too cute?\n" +
                        "  ###\n" +
                        "  TOPIC: VIDEO GAMES\n" +
                        "  - You look like the type of girl I want to be a Pokemon trainer to.\n" +
                        "  ###\n" +
                        "  TOPIC: TRAVEL\n" +
                        "  - I want some of your culture.\n" +
                        "  ###\n" +
                        "  TOPIC: TRAVEL\n" +
                        "  - I want to explore every part of you.\n" +
                        "  ###\n" +
                        "  TOPIC: STARCRAFT\n" +
                        "  - I'm glad I scouted you.\n" +
                        "  ###\n" +
                        "  TOPIC: TRAVEL\n" +
                        "  - I think your exotic.\n" +
                        "  ###\n" +
                        "  TOPIC: TRAVEL\n" +
                        "  - I'm at the airport, looking at the plane schedule. Now all I need is you.\n" +
                        "  ###\n" +
                        "  TOPIC: TACOS AND INNUENDO\n" +
                        "  - You look like the type that would be good with a little hot sauce.\n" +
                        "  ###\n" +
                        "  TOPIC: ASTRONAUTS\n" +
                        "  - I think we should make space travel plans.\n" +
                        "  ###\n" +
                        "  TOPIC: ASTRONAUTS AND INNUENDO\n" +
                        "  - I'd like to be your space shuttle.\n" +
                        "  ###\n" +
                        "  TOPIC: COFFEE\n" +
                        "  - Do you like coffee? Cuz I like you a latte.\n" +
                        "  ###\n" +
                        "  TOPIC: " + selectedText;
                data = String.format(template , context , "0.6" , "0.7");
                break;
            case 806:
                String selectedTextS = selectedText + "";
                String[] lines = selectedTextS.split("\\r?\\n");
                context = "The following email explains two things:\n" +
                        "    1) "+lines[0]+"\n" +
                        "    2) "+lines[1]+"\n" +
                        "\n" +
                        "    From:";
                data = String.format(template , context , "0.9" , "0.6");
                break;
            case 807:
                context = "Below is a selection of 10 poems written by the latest cutting-edge contemporary poets They cover every topic from the Singularity to the four seasons to human mortality, featuring remarkable use of metaphor, rhyme, and meter.\n" + selectedText + "\n";
                data = String.format(template , context , "0.9" , "0.7");
                break;
            case 808:
                context = "To be fair, you have to have a very high IQ to understand Rick and Morty. The humor is extremely subtle, and without a solid grasp of theoretical physics most of the jokes will go over a typical viewer’s head. There’s also Rick’s nihilistic outlook, which is deftly woven into his characterisation—his personal philosophy draws heavily from Narodnaya Volya literature, for instance. The fans understand this stuff; they have the intellectual capacity to truly appreciate the depths of these jokes, to realize that they’re not just funny—they say something deep about LIFE. As a consequence people who dislike Rick and Morty truly ARE idiots—of course they wouldn’t appreciate, for instance, the humour in Rick’s existencial catchphrase “Wubba Lubba Dub Dub,” which itself is a cryptic reference to Turgenev’s Russian epic Fathers and Sons I’m smirking right now just imagining one of those addlepated simpletons scratching their heads in confusion as Dan Harmon’s genius unfolds itself on their television screens. What fools… how I pity them. ðŸ˜‚ And yes by the way, I DO have a Rick and Morty tattoo. And no, you cannot see it. It’s for the ladies’ eyes only—And even they have to demonstrate that they’re within 5 IQ points of my own (preferably lower) beforehand.\n" +
                        "\n" +
                        "To be fair, you have to have a very high IQ to understand cuckolding. The kink is extremely subtle, and without a solid grasp of intersectional feminism most of the empowerment will go over a typical cuck’s head. There’s also the Bull’s aggressive outlook, which is deftly woven into his role—his personal mannerisms draw heavily from the mating habits of bovine animals, for instance. The cucks understand this stuff; they have the intellectual capacity to truly appreciate the depths of being cuckolded, to realize that it’s not just arousing—it says something deep about DIVERSITY. As a consequence people who dislike cuckolding truly ARE idiots—of course they wouldn’t appreciate, for instance, the power of when the woman says “leave the room Carl you’re breaking Tyrone’s concentration,” which itself is a cryptic reference to the plight of African-American males in the United States. I’m smugly grinning right now just imagining one of those addlepated simpletons scratching their heads in confusion as The Bull’s strong African seed ejaculates itself on my wife. What bigots… how I pity them. ðŸ˜‚ And yes by the way, I DO have a cuck tattoo. And no, you cannot see it. It’s for The Bull’s eyes only—And even they have to demonstrate that they’re within 7 inches above of my own (preferably higher) beforehand.\n" +
                        "\n" +
                        "To be fair, you have to have a very high IQ to understand The Last Of Us 2. The story is extremely subtle, and without a solid grasp of theoretical storytelling, most of the story will go over a typical player’s head. There’s also Abby’s nihilistic outlook, which is deftly woven into her characterisation—her personal philosophy draws heavily from Anita Sarkeesian literature, for instance. The fans understand this stuff; they have the intellectual capacity to truly appreciate the depths of these characters, to realise that they’re not just underwritten—they say something deep about LIFE. As a consequence people who dislike The Last Of Us 2 truly ARE idiots—of course they wouldn’t appreciate, for instance, the storytelling in Abby’s existential sex scene “I’m gonna fuck a committed dude who’s gf is pregnant” which itself is a cryptic reference to Neil Druckmanns epic fetishes. I’m smirking right now just imagining one of those addlepated simpletons scratching their heads in confusion as Abby’s nine iron unfolds Joel’s head on their television screens. What fools… how I pity them. ðŸ˜‚ And yes, by the way, i DO have an Abby tattoo. And no, you cannot see it. It’s for the buff ladies’ eyes only—and even then they have to demonstrate that they’re within 5 IQ points of my own (preferably lower) beforehand. Nothin personnel kid ðŸ˜Ž\n" +
                        "\n" +
                        "To be fair, you have to have a very high IQ to understand Wildbow. His worldbuilding is extremely subtle, and without a solid grasp of theoretical bullying most of the torture porn will go over a typical reader’s head. There’s also Taylor’s cardboard outlook, which is deftly woven into her charactization as a Mary Sue—her personal philosophy draws heavily from the trashcan, for instance. The fans understand this stuff; they are wild for the Wildbow. They have the intellectual capacity to truly appreciate the depths of his meandering prose, to realize that he’s not just telling me exactly what Taylor is thinking at any given moment, he says something deep about NOTHING. As a consequence people who dislike Wildbow truly ARE idiots—of course they wouldn’t appreciate, for instance, the humour in Taylor’s existential catchphrase “Take that, you worm!” which itself is a cryptic reference to a publishing deal that will never happen. I’m smirking right now just imagining one of those addlepated simpletons scratching their heads in confusion as Wildbow’s genius wit unfolds itself on their Kindle readers. What fools… how I pity them.\n" +
                        "\n" +
                        "To be fair, you have to have a very high IQ to understand " + selectedText;
                data = String.format(template , context , "0.9" , "0.7");
                break;
            default:
                System.out.println("Something went terribly wrong!");
                context = "";
                data = "";
                break;
        }
        System.out.println(data);
        outStrings[0] = context; outStrings[1] = data;
        return outStrings;
    }

    @Override
    public void onKey(int primaryCode, int[] keyCodes) {
        if (primaryCode == 809){
            InputMethodManager imeManager = (InputMethodManager) getApplicationContext().getSystemService(INPUT_METHOD_SERVICE);
            imeManager.showInputMethodPicker();
        }
        else if (primaryCode == 805) {
            Intent intent = new Intent();
            intent.setClassName("com.termux", "com.termux.app.RunCommandService");
            intent.setAction("com.termux.RUN_COMMAND");
            intent.putExtra("com.termux.RUN_COMMAND_PATH", "/data/data/com.termux/files/home/google-cloud-sdk/bin/gcloud");
            intent.putExtra("com.termux.RUN_COMMAND_ARGUMENTS",  new String[] {"alpha compute tpus tpu-vm ssh riddles-tpu --zone europe-west4-a -- -NL 8888:localhost:5000"});
            intent.putExtra("com.termux.RUN_COMMAND_BACKGROUND",  true);
            startService(intent);
        }
        else if (primaryCode >= 800) {
            int TIMEOUT_MS = 100000; //100 seconds
            InputConnection ic = getCurrentInputConnection();
            if (ic == null) return;
            CharSequence selectedText = ic.getSelectedText(0);
            System.out.println(selectedText);
            String url = "http://localhost:8888/complete";
            RequestQueue requestQueue = Volley.newRequestQueue(this);
            String[] outStrings = get_data_from_text(selectedText, primaryCode);
            String data = outStrings[1];
            String context = outStrings[0];
            JsonObjectRequest jsonobj = null;
            try {
                jsonobj = new JsonObjectRequest(Request.Method.POST, url, new JSONObject(data),
                        response -> {
                            System.out.println(response.toString());
                            try {
                                InputConnection ic_new = getCurrentInputConnection();
                                ic_new.deleteSurroundingText(4 , 0);
                                ic_new.commitText(context + response.getString("completion"), 1);
                            } catch (JSONException e) {
                                e.printStackTrace();
                            }
                        },
                        error -> ic.commitText(error.toString(),1)
                );
            } catch (JSONException e) {
                ic.commitText(e.toString(),1);
            }
            jsonobj.setRetryPolicy(new DefaultRetryPolicy(
                    TIMEOUT_MS,
                    DefaultRetryPolicy.DEFAULT_MAX_RETRIES,
                    DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
            requestQueue.add(jsonobj);

            ic.commitText("🧠🦄", 1);
        } else {
            InputConnection inputConnection = getCurrentInputConnection();
            if (inputConnection != null) {
                switch(primaryCode) {
                    case Keyboard.KEYCODE_DELETE :
                        CharSequence selectedText = inputConnection.getSelectedText(0);
                        if (TextUtils.isEmpty(selectedText)) {
                            inputConnection.deleteSurroundingText(1, 0);
                        } else {
                            inputConnection.commitText("", 1);
                        }
                    case Keyboard.KEYCODE_SHIFT:
                        caps = !caps;
                        keyboard.setShifted(caps);
                        keyboardView.invalidateAllKeys();
                        break;
                    case Keyboard.KEYCODE_DONE:
                        inputConnection.sendKeyEvent(new KeyEvent(KeyEvent.ACTION_DOWN, KeyEvent.KEYCODE_ENTER));
                        break;
                    default :
                        char code = (char) primaryCode;
                        if(Character.isLetter(code) && caps){
                            code = Character.toUpperCase(code);
                        }
                        inputConnection.commitText(String.valueOf(code), 1);

                }
            }

        }
    }

    @Override
    public void onPress(int primaryCode) { }

    @Override
    public void onRelease(int primaryCode) { }

    @Override
    public void onText(CharSequence text) { }

    @Override
    public void swipeLeft() { }

    @Override
    public void swipeRight() { }

    @Override
    public void swipeDown() { }

    @Override
    public void swipeUp() { }
}