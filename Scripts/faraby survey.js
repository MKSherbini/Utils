(function FkingDoIT() {
var itemsA = document.getElementsByClassName('listItems in collapse');

for (var i = 0, max = itemsA.length; i < max; i ++) {
	
	for (var j = 0, max = itemsA[i].childElementCount; j < max; j ++) {
		
		itemsA[i].children[j].click();
		
		var inputs = document.querySelectorAll("input[type='radio']")
		for (var i = 0, max = inputs.length; i < max; i = i +5) {
			inputs[i].click();
		}
		
		document.querySelector("input[type='checkbox']").click();
		
		saveQz($(this), '58749747376ed1705eae50d4', '1');
		setTimeout(FkingDoIT, 2000);
	}
}
}()); 