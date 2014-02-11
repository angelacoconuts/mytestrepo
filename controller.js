var graphieeApp = angular.module('graphieeApp', []);

graphieeApp.controller('poiPageCtrl', ['$scope',
function ($scope) {

	$scope.showAddAction = false;

	$scope.pics = [
	{'src': 'P://Personal/example.jpg',
	'title': 'Sunset in Cook Island'},
	{'src': 'P://Personal/example (1).jpg',
	'title': 'Bird Eye View'},
	{'src': 'P://Personal/example (2).jpg',
	'title': 'Colorful flower'},
	{'src': 'P://Personal/example (3).jpg',
	'title': 'Sandy beach'}
	];

	$scope.mainPicSrc = $scope.pics[0].src;
	$scope.mainPicTle = $scope.pics[0].title;

	$scope.tags = [
	{'name': 'Beach',
		'href': '#Beach',
	'color': '#5bc0de'},
	{'name': 'Resort',
		'href': '#Resort',
	'color': '#f0ad4e'},
	{'name': 'Diving',
		'href': '#Diving',
	'color': '#d9534f'},
	{'name': 'Nature',
		'href': '#Nature',
	'color': '#47a447'}
	];
	
	$scope.related = [
	{'img': 'P://Personal/example.jpg',
	'name': 'Bali',
	'desc': 'Sunset in Cook Island'},
	{'img': 'P://Personal/example (1).jpg',
	'name': 'Greece',
	'desc': 'Bird Eye View'},
	{'img': 'P://Personal/example (2).jpg',
	'name': 'Seychelle',
	'desc': 'Colorful flower'},
	{'img': 'P://Personal/example (3).jpg',
	'name': 'Hawaii',
	'desc': 'Sandy beach'}
	];

	$scope.poiTle = "Cook Islands";

	$scope.poiDesc = "The Cook Islands is a parliamentary democracy in the South Pacific Ocean in free association with New Zealand. It comprises 15 small islands whose total land area is 240 square kilometres . The Cook Islands' Exclusive Economic Zone (EEZ), however, covers 1,800,000 square kilometres of ocean. The Cook Islands' defence and foreign affairs are the responsibility of New Zealand, in consultation with the Cook Islands.";

	$scope.setImage = function(image) {
		$scope.mainPicSrc = image.src;
		$scope.mainPicTle = image.title;
	}

	$scope.showActionList = function() {
		if($scope.showAddAction)
		$scope.showAddAction = false;
		else
			$scope.showAddAction = true;
		}

	}]);
