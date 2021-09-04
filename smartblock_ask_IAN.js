function htmlEntities(str) {
    return String(str).replace(/&/g, '&amp;').replace(/</g, '&lt;').replace(/>/g, '&gt;').replace(/"/g, '&quot;');
}
var prompt = document.activeElement.value.replaceAll('  ',' ').replaceAll('  ',' ').replaceAll('\n','\\n');
prompt = htmlEntities(prompt)
console.log(prompt)
var url = "http://localhost:YOUR_LOCAL_PORT_HERE/complete";
var xhr = new XMLHttpRequest();
var cUID = document.activeElement["id"].slice(-9);
var fromUID = cUID.replace('((','').replace('))','');
var blockInfo = await roam42.common.getBlockInfoByUID(fromUID);
xhr.open("POST", url , 1);
xhr.onload = function (e) {
  if (xhr.readyState === 4) {
    if (xhr.status === 200) {
      var responseText = JSON.parse(xhr.responseText);
      console.log(responseText["completion"])
      responseString = responseText["completion"];
      responseString = responseString.replaceAll("*","**").replaceAll("_","__");
      responseString = responseString.replaceAll('#[[','__').replaceAll('[[','__').replaceAll(']]','__').replaceAll('#','');
      responseString = responseString.replaceAll('{{','{').replaceAll('}}','}').replaceAll('  ',' ').replaceAll('::',':');
      responseArr = responseString.replaceAll("\n ","\n").split("\n").filter(item => item);
      roam42.common.batchCreateBlocks(cUID , Number(blockInfo.order) + 2 , responseArr )
    } else {
      console.error(xhr.statusText);
    }
  }
};
xhr.onerror = function (e) {
  console.error(xhr.statusText);
};
xhr.setRequestHeader("Content-Type", "application/json");
var data = '{"context": "' + prompt + '", "top_p": 0.9, "temp": 0.85}';
xhr.send(data);
return ''
