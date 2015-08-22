function userController($scope, $http) {
    $scope.pageToGet = 0;

    $scope.state = 'busy';

    $scope.lastAction = '';

    $scope.url = "/user/users/";

    $scope.errorOnSubmit = false;
    $scope.errorIllegalAccess = false;
    $scope.displayMessageToUser = false;
    $scope.displayValidationError = false;
    $scope.displaySearchMessage = false;
    $scope.displaySearchButton = false;
    $scope.displayCreateUserButton = false;

    $scope.contact = {}

    $scope.searchFor = ""

    $scope.getContactList = function () {
	
	alert("inside getContactList ");
        var url = $scope.url;
        $scope.lastAction = 'list';

        $scope.startDialogAjaxRequest();

        var config = {params: {page: $scope.pageToGet}};
		alert("inside getContactList url " + url);
        $http.get(url, config)
            .success(function (data) {
				alert("Success");
                $scope.finishAjaxCallOnSuccess(data, null, false);
            })
            .error(function () {
				alert("Error");
                $scope.state = 'error';
                $scope.displayCreateContactButton = false;
            });
    }
	
    $scope.populateTable = function (data) {
    	alert("Inside populateTable");
        if (data.pagesCount > 0) {
            $scope.state = 'list';

            $scope.page = {source: data.users, currentPage: $scope.pageToGet, pagesCount: data.pagesCount, totalUsers : data.totalUsers};
			if($scope.page.pagesCount <= $scope.page.currentPage){
				alert("Inside if");
                $scope.pageToGet = $scope.page.pagesCount - 1;
                $scope.page.currentPage = $scope.page.pagesCount - 1;
            }

            $scope.displayCreateUserButton = true;
            $scope.displaySearchButton = true;
        } else {
            $scope.state = 'noresult';
            $scope.displayCreateUserButton = true;

            if(!$scope.searchFor){
                $scope.displaySearchButton = false;
            }
        }

        if (data.actionMessage || data.searchMessage) {
            $scope.displayMessageToUser = $scope.lastAction != 'search';

            $scope.page.actionMessage = data.actionMessage;
            $scope.page.searchMessage = data.searchMessage;
        } else {
            $scope.displayMessageToUser = false;
        }
    }

    $scope.changePage = function (page) {
		alert("Inside change page" + page);
        $scope.pageToGet = page;

        if($scope.searchFor){
            $scope.searchContact($scope.searchFor, true);
        } else{
            $scope.getContactList();
        }
    };

    $scope.searchContact = function (searchContactForm, isPagination) {
		alert("inside search for");
	};


    $scope.finishAjaxCallOnSuccess = function (data, modalId, isPagination) {
		alert("Inside finishAjaxCallOnSuccess");
        $scope.populateTable(data);
        $("#loadingModal").modal('hide');

        if(!isPagination){
            if(modalId){
                $scope.exit(modalId);
            }
        }

        $scope.lastAction = '';
    }

    $scope.startDialogAjaxRequest = function () {
        $scope.displayValidationError = false;
        $scope.previousState = $scope.state;
        $scope.state = 'busy';
    }

	
    $scope.handleErrorInDialogs = function (status) {
		alert(status);
        $("#loadingModal").modal('hide');
        $scope.state = $scope.previousState;

        // illegal access
        if(status == 403){
        	$scope.errorIllegalAccess = true;
            return;
        }

        $scope.errorOnSubmit = true;
        $scope.lastAction = '';
    }


    $scope.createUser = function (newUserForm) {
		alert("Inside createUser");
        if (!newUserForm.$valid) {
            $scope.displayValidationError = true;
            return;
        }

        $scope.lastAction = 'create';

        var url = $scope.url;

        var config = {headers: {'Content-Type': 'application/x-www-form-urlencoded; charset=UTF-8'}};
        $http.post(url, $.param($scope.user), config)
            .success(function (data) {
                $scope.finishAjaxCallOnSuccess(data, "#addUsersModal", false);
            })
            .error(function(data, status, headers, config) {
                $scope.handleErrorInDialogs(status);
            });
    };

}
