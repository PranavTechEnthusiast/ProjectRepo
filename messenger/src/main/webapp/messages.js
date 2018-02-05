var msgContainer = document.getElementById("msgs");

var btn=document.getElementById("btn");
btn.addEventListener("click",function(){
	var req=new XMLHttpRequest();
	req.open("GET","http://localhost:8085/messenger/webapi/messages");
	req.onload= function(){
		var resp= JSON.parse(req.responseText);
		renderHTML(resp);
	};
	req.send();
});

function renderHTML(data){
	var content= "";
	
	for(i=0;i<data.length;i++){
		content+="<p> Id:" +data[i].id+" and message is" +data[i].message+ "</p>";
	}
	msgContainer.insertAdjacentHTML('beforeend',content);
}