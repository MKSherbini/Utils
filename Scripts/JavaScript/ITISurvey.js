var star4=document.querySelectorAll('[id*=Star_4]');
for(var i=star4.length-1;i>=0;i-- ){
  star4[i].dispatchEvent(new Event('mouseover'));
  star4[i].dispatchEvent(new Event('click'));
}
