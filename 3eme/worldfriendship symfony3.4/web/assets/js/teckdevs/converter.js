function httpGet(theUrl)
{
    var xmlHttp = null;
    xmlHttp = new XMLHttpRequest();
    xmlHttp.open( "GET", theUrl, false );
    xmlHttp.send( null );
    return xmlHttp.responseText;
}

var getBitApi = httpGet('https://api.coindesk.com/v1/bpi/currentprice/BTC.json');

getBitApi = JSON.parse(getBitApi);

console.log(getBitApi);
var bitCoinCurrentRate = getBitApi.bpi.USD.rate_float;

$(document).ready(function(){
	$("#bit-l-price").html(bitCoinCurrentRate.toFixed(2) + ' $');
	$('#btcc').keyup(function(){
		var uVal = parseFloat($("#btcc").val());
		if(isNaN(uVal)) {
			$('#usdc').val(0);
		} else {
			$('#usdc').val(uVal * bitCoinCurrentRate);
		}	
	});
	
});
