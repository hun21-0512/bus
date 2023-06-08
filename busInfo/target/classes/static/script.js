function speak(text) {
    if (typeof SpeechSynthesisUtterance === "undefined" || typeof window.speechSynthesis === "undefined") {
        alert("이 브라우저는 음성 합성을 지원하지 않습니다.")
        return
    }
    
    window.speechSynthesis.cancel() // 현재 읽고있다면 초기화

    const speechMsg = new SpeechSynthesisUtterance();
    speechMsg.rate = 1.3    
    speechMsg.pitch = 1.2
    speechMsg.lang = "ko-KR"
    speechMsg.text = text
    
    window.speechSynthesis.speak(speechMsg);
}

function getDay() {
	let today = new Date();   
	let year = today.getFullYear();
	let month = today.getMonth() + 1;
	let date = today.getDate();
	dayList = ["일","월","화","수","목","금","토"];
	let day=dayList[today.getDay()];
	if (today.getHours() == 12) {
		let hours = "오후 12";
	} else if (today.getHours() > 12) {
		let hours = "오후 "+today.getHours()-12;
	} else {
		let hours = "오전 "+today.getHours();
	}
	let hours = today.getHours();
	let minutes = today.getMinutes();
	
	return year+"년"+month+"월"+date+"일"+day+"요일, "+hours+"시"+minutes+"분"
}

function chat() {
	speak("안녕하세요. 웹 페이지 봇입니다. 무엇을 도와드릴까요?");
}

function busInfo() {
	speak(getDay()+"현재 운행중인 버스는 1대이며, 현재 위치에서 가장 가까운 정류장은 '도림캠퍼스'이고, 정류장까지 도보로 약 5분정도 소요됩니다. 10분 뒤에 '도림캠퍼스 정류장'에 버스가 도착합니다. 정류장까지 경로안내를 시작할까요?");
}

function busInfo1() {
	speak(getDay()+"A권역의 A마을로 가는 버스는 E정류장에서 F정류장으로 이동중입니다.");
}

function busInfo2() {
	speak(getDay()+"B권역의 B마을로 가는 버스는 D정류장에 도착하였습니다.");
}

function busInfo3() {
	speak(getDay()+"C권역의 C마을로 가는 버스는 D정류장에서 E정류장으로 이동중입니다.");
}

function loginBot() {
	speak("로그인을 하기 위해 지문인식을 시작합니다");
}

function communityBot() {
	speak("커뮤니티에 n개의 글이 있습니다, "+getDay()+"가장 인기있는 글은 00이며, 추가적으로 글작성이나 글 읽기 등을 위해 저를 불러주세요");
}