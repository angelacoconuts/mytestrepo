var epic = angular.module('epic', []);

epic.controller('epicCtrl', ['$scope',
function ($scope) {

	$scope.ranges = [
		"WTD","MTD","YTD"
	];
	
	$scope.currencies = [
		{name:"USD",sign:"$"},
		{name:"HKD",sign:"$"},
		{name:"CNY",sign:"¥"},
		{name:"EUR",sign:"€"},
		{name:"GBP",sign:"£"},
	];
	
	$scope.currency = $scope.currencies[0].name;
	$scope.currency_sign = $scope.currencies[0].sign;
	$scope.time_range = $scope.ranges[2];

	$scope.articles = [
	{'img': 'P://MyDocuments/EWM/Mockup/Library/img/Asian_Stocks.jpg',
	'title': 'Asian Stocks Rise to Highest in Month as Yuan Drops',
	'label': 'CIO Insight',
	'color': '#333300'},
	{'img': 'P://MyDocuments/EWM/Mockup/Library/img/Ford_Planning.jpg',
	'title': 'Ford Planning to Drop Microsoft for BlackBerry in Its Car Technology System',
	'label': 'UBS Research',
	'color': '#003300'},
	{'img': 'P://MyDocuments/EWM/Mockup/Library/img/Netflix_Said.jpg',
	'title': 'Netflix Said to Agree to Pay Comcast for Faster Access',
	'label': 'Market News',
	'color': '#4c0000'},
	{'img': 'P://MyDocuments/EWM/Mockup/Library/img/Ukraine_Replaces.jpg',
	'title': 'Ukraine Replaces Central Bank Head',
	'label': 'Impact News',
	'color': '#444444'}
	];
	
	$scope.assets = [
	   { "name": "UBS", "volume":3157394, "perf":2.19 },
	   { "name": "DBS", "volume":223663, "perf":2.91 },
	   { "name": "Oil", "volume":57894125, "perf":1.69 },
	   { "name": "Gold", "volume":37213285, "perf":-3.15 },
	   { "name": "Corn", "volume":213157, "perf":2.20 }
	];
	
	$scope.format = function(currency_sign, n){
    return currency_sign + " " + n.toFixed(2).replace(/./g, function(c, i, a) {
        return i > 0 && c !== "." && (a.length - i) % 3 === 0 ? "," + c : c;
    });
   };
   
  $scope.chgRange = function(selection){
  		$scope.time_range = selection;
  };
	
	$scope.chgCurrency = function(selection){
			$scope.currency = selection.name;
			$scope.currency_sign = selection.sign;
  };
	
}]);
