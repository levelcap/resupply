var timePeriods = [
	{id: "MEDIEVAL", name: "Medieval"},
    {id: "VICTORIAN", name: "Victorian"},
    {id: "MODERN", name: "Modern"},
    {id: "FUTURE", name: "Future"}
];
              	
var genres = [
	{id: "FANTASY", name: "Fantasy"},
    {id: "REALISTIC", name: "Realistic"},
    {id: "SCIFI", name: "Science Fiction"},
    {id: "ANIME", name: "Anime"}
];

var chardbApp = angular.module('chardbApp', ['textAngular', 'ngImgur']);

chardbApp.controller('CharacterController', function ($scope, $http, $sce) {
	var id= $("#objId").val();
	$scope.timePeriods = timePeriods;
	$scope.genres = genres;

    $http.get('/api/character/' + id).
        success(function (data) {
        	if (data == null || data == "") {
        		$scope.char = {};
        	} else {
            	$scope.char = data;
        	}	
            if (null == $scope.char.url) {
            	$scope.char.url = "/images/blank.png";
            }
            $scope.char.description = $sce.trustAsHtml($scope.char.description);
            $scope.orightml = $scope.char.description;
            $scope.htmlcontent = $scope.orightml;
            $scope.disabled = false;
        });

    $scope.save = function () {
        var saveCharacter = $scope.char;
        if ($scope.htmlcontent !== null && $scope.htmlcontent !== "") {
        	saveCharacter.description = $scope.htmlcontent.toString();
        } else {
        	saveCharacter.description = "";
        }
        saveCharacter.id = $("#objId").val();
        $http.post('/api/character/' + id, saveCharacter).
            success(function (data, status, headers, config) {
            	alert("Save success");
            }).
            error(function (data, status, headers, config) {
            	alert("Save failure");
            });
    }
    
    $scope.delete = function () {
    	$http.delete('/api/character/' + id).
        success(function (data, status, headers, config) {
        	alert("Delete success");
        	window.location.href = "/user";
        }).
        error(function (data, status, headers, config) {
        	alert("Delete failure");
        });
    }
});

chardbApp.controller('LocationController', function ($scope, $http, $sce) {
	var id= $("#objId").val();
	$scope.timePeriods = timePeriods;
	$scope.genres = genres;

    $http.get('/api/location/' + id).
        success(function (data) {
        	if (data == null || data == "") {
        		$scope.char = {};
        	} else {
            	$scope.char = data;
        	}	
            if (null == $scope.char.url) {
            	$scope.char.url = "/images/blank.png";
            }
            $scope.char.description = $sce.trustAsHtml($scope.char.description);
            $scope.orightml = $scope.char.description;
            $scope.htmlcontent = $scope.orightml;
            $scope.disabled = false;
        });

    $scope.save = function () {
        var saveCharacter = $scope.char;
        if ($scope.htmlcontent !== null && $scope.htmlcontent !== "") {
        	saveCharacter.description = $scope.htmlcontent.toString();
        } else {
        	saveCharacter.description = "";
        }
        saveCharacter.id = $("#objId").val();
        $http.post('/api/location/' + id, saveCharacter).
            success(function (data, status, headers, config) {
            	alert("Save success");
            }).
            error(function (data, status, headers, config) {
            	alert("Save failure");
            });
    }
    
    $scope.delete = function () {
    	$http.delete('/api/location/' + id).
        success(function (data, status, headers, config) {
        	alert("Delete success");
        	window.location.href = "/user";
        }).
        error(function (data, status, headers, config) {
        	alert("Delete failure");
        });
    }
});

chardbApp.controller('SettingController', function ($scope, $http, $sce) {
	var id= $("#objId").val();
	$scope.timePeriods = timePeriods;
	$scope.genres = genres;

    $http.get('/api/setting/' + id).
        success(function (data) {
        	if (data == null || data == "") {
        		$scope.char = {};
        	} else {
            	$scope.char = data;
        	}	
            if (null == $scope.char.url) {
            	$scope.char.url = "/images/blank.png";
            }
            $scope.char.description = $sce.trustAsHtml($scope.char.description);
            $scope.orightml = $scope.char.description;
            $scope.htmlcontent = $scope.orightml;
            $scope.disabled = false;
        });

    $scope.save = function () {
        var saveCharacter = $scope.char;
        if ($scope.htmlcontent !== null && $scope.htmlcontent !== "") {
        	saveCharacter.description = $scope.htmlcontent.toString();
        } else {
        	saveCharacter.description = "";
        }
        saveCharacter.id = $("#objId").val();
        $http.post('/api/setting/' + id, saveCharacter).
            success(function (data, status, headers, config) {
            	alert("Save success");
            }).
            error(function (data, status, headers, config) {
            	alert("Save failure");
            });
    }
    
    $scope.delete = function () {
    	$http.delete('/api/setting/' + id).
        success(function (data, status, headers, config) {
        	alert("Delete success");
        	window.location.href = "/user";
        }).
        error(function (data, status, headers, config) {
        	alert("Delete failure");
        });
    }
    
    $scope.deleteMember = function(memberId) {
    	for (var i = 0; i < $scope.char.members.length; i++) {
    		if ($scope.char.members[i].id == memberId) {
    			$scope.char.members.splice(i,1);
    		}
    	}
    }
    
    $scope.deleteLocation = function(locationId) {
    	for (var i = 0; i < $scope.char.locations.length; i++) {
    		if ($scope.char.locations[i].id == locationId) {
    			$scope.char.locations.splice(i,1);
    		}
    	}
    }
    
    $scope.tab = 1;

    $scope.setTab = function (tabId) {
    	$scope.tab = tabId;
    };

    $scope.isSet = function (tabId) {
        return $scope.tab === tabId;
    };    
});

chardbApp.controller('RegistrationController', function ($scope, $http, $sce) {
    $scope.submit = function () {
        var saveUser = {};
        saveUser.email = $scope.email;
        saveUser.password = $scope.password;
        
        $http.post('/api/user', saveUser).
            success(function (data, status, headers, config) {
            	window.location.href = "/login";
            }).
            error(function (data, status, headers, config) {
            	alert("A user with that email already exists");
            });
    }
});

chardbApp.controller('UserController', function ($scope, $http, $sce) {
	$scope.username = $("#currentName").val();
    $scope.save = function () {
        $http.post('/api/user/username', $scope.username).
            success(function (data, status, headers, config) {
            	alert("Save success");
            }).
            error(function (data, status, headers, config) {
            	if (status == 400) {
            		alert("Sorry, that name is already in use!");
            	} else {
            		alert("Save failed");
            	}
            });
    }
});

/** Text options 
chardbApp.config(['$provide', function($provide) {				
	     $provide.decorator('taOptions', ['$delegate', function(taOptions) {				
	         taOptions.toolbar = [		
	             ['h1', 'h2', 'h3', 'h4', 'h5', 'h6', 'p', 'pre', 'quote'],		
	             ['bold', 'italics', 'underline', 'ul', 'ol', 'redo', 'undo', 'clear'],		
	             ['justifyLeft','justifyCenter','justifyRight'],		
	         ];		
	         return taOptions;		
	     }]);		
}]);
**/

chardbApp.directive('drop', function ($timeout, imgur) {
    return {
        restrict: 'EAC',
        scope: true,
        link: function link($scope, element) {
            imgur.setAPIKey('Client-ID 7ad9e6431502c89');
            $scope.preventDefaultBehaviour = function preventDefaultBehaviour(event) {
                event.preventDefault();
                event.stopPropagation();
            };
            element.on('drop', function onDrag(event) {
                $scope.preventDefaultBehaviour(event);
                var image = event.dataTransfer.files[0];

                imgur.upload(image).then(function then(model) {
                    $scope.char.url = model.link;
                });

            });
            // Prevent the default behaviour on certain events.
            element.on('dragover dragend dragleave', $scope.preventDefaultBehaviour);
        }
    };
});

