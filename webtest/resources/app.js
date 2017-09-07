var app = angular.module('plc', []);

app.config(['$httpProvider', function($httpProvider) {
    $httpProvider.defaults.xsrfCookieName = 'csrftoken';
    $httpProvider.defaults.xsrfHeaderName = 'X-CSRFToken';
}]);

app.controller('plcCtrl', function($scope, $http) {
	var asJSON = function(s) {
		return JSON.stringify(s, null, 4);
	};
	var setSuccessMsg = function(data, status) {
		$scope.response = status + "\r\n" + asJSON(data);
		//$("#tree").treeview(data);
	};
	var setErrorMsg = function(request, status, error) {
		$scope.response = status + "\r\n" + error;
	};

	var itemData = {
		"CREATE": [{
			"BUSINESS_OBJECT": "Item",
			"COLUMN_ID": "CUST_WXC",
			"SIDE_PANEL_GROUP_ID": 101,
			"PATH": "Item",
			"ATTRIBUTES": [{
				"BUSINESS_OBJECT": "Item",
				"COLUMN_ID": "CUST_WXC",
				"PATH": "Item",
				"ITEM_CATEGORY_ID": 2
			}],
			"TEXT": [{
				"PATH": "Item",
				"COLUMN_ID": "CUST_WXC",
				"LANGUAGE": "EN"
			},
			{
				"PATH": "Item",
				"COLUMN_ID": "CUST_WXC",
				"LANGUAGE": "JA-JP"
			},
			{
				"PATH": "Item",
				"COLUMN_ID": "CUST_WXC",
				"LANGUAGE": "ZH-TW"
			},
			{
				"PATH": "Item",
				"COLUMN_ID": "CUST_WXC",
				"LANGUAGE": "DE"
			},
			{
				"PATH": "Item",
				"COLUMN_ID": "CUST_WXC",
				"LANGUAGE": "ZH-CN"
			}],
			"REF_UOM_CURRENCY_BUSINESS_OBJECT": "",
			"REF_UOM_CURRENCY_COLUMN_ID": "",
			"REF_UOM_CURRENCY_PATH": "",
			"ROLLUP_TYPE_ID": 0,
			"SEMANTIC_DATA_TYPE": "String",
			"UOM_CURRENCY_FLAG": 0,
			"FORMULAS": []
		}],
		"UPDATE": [],
		"DELETE": []
	};
	
	var matierialData = {
		"CREATE": [{
			"BUSINESS_OBJECT": "Material",
			"COLUMN_ID": "CMAT_ATTR1",
			"SIDE_PANEL_GROUP_ID": 501,
			"PATH": "Material",
			"ATTRIBUTES": [{
				"BUSINESS_OBJECT": "Material",
				"COLUMN_ID": "CMAT_ATTR1",
				"PATH": "Material",
				"ITEM_CATEGORY_ID": -1,
				"DEFAULT_VALUE": "test"
			}],
			"TEXT": [{
				"PATH": "Material",
				"COLUMN_ID": "CMAT_ATTR1",
				"LANGUAGE": "EN"
			}, {
				"PATH": "Material",
				"COLUMN_ID": "CMAT_ATTR1",
				"LANGUAGE": "JA-JP"
			}, {
				"PATH": "Material",
				"COLUMN_ID": "CMAT_ATTR1",
				"LANGUAGE": "ZH-TW"
			}, {
				"PATH": "Material",
				"COLUMN_ID": "CMAT_ATTR1",
				"LANGUAGE": "DE"
			}, {
				"PATH": "Material",
				"COLUMN_ID": "CMAT_ATTR1",
				"LANGUAGE": "ZH-CN"
			}],
			"REF_UOM_CURRENCY_BUSINESS_OBJECT": "",
			"REF_UOM_CURRENCY_COLUMN_ID": "",
			"REF_UOM_CURRENCY_PATH": "",
			"ROLLUP_TYPE_ID": 0,
			"SEMANTIC_DATA_TYPE": "String",
			"UOM_CURRENCY_FLAG": 0,
			"FORMULAS": []
		}],
		"UPDATE": [],
		"DELETE": []
	};
	
	var apis = [{
		action: "initSession",
		name: "init-session",
		url: "/sap/plc/xs/rest/dispatcher.xsjs/init-session?language=EN",
		method: "POST"
	}, {
		action: "ping",
		name: "ping",
		url: "/sap/plc/xs/rest/dispatcher.xsjs/ping",
		method: "GET"
	}, {
		action: "logout",
		name: "logout",
		url: "/sap/plc/xs/rest/dispatcher.xsjs/logout",
		method: "POST"
	}, {
		action: "customfieldsFormulaGet",
		name: "customfieldsformula",
		url: "/sap/plc/xs/rest/dispatcher.xsjs/customfieldsformula?business_object=Item&is_custom=true&lock=true",
		method: "GET"
	}, {
		action: "customfieldsFormulaCreate",
		name: "customfieldsformula",
		url: "/sap/plc/xs/rest/dispatcher.xsjs/customfieldsformula",
		// url: "/sap/plc/xs/rest/dispatcher.xsjs/customfieldsformula?checkCanExecute=true",
		// url: "/test.xsjs?a=1&b=2",
		method: "POST",
		data: itemData
	}];

	angular.forEach(apis, function(api) {
		$scope[api.action] = function() {
			$scope.httpHeadLine = api.method + " " + api.url;
			$scope.response = "";
			$.ajax({
				async: false,
				url: api.url,
				dataType: "json",
				type: api.method,
				contentType: "application/json",
				data: JSON.stringify(api.data),
				// data: api.data,
				
				success: setSuccessMsg,
				error: setErrorMsg
			});

			// Not work: it might be xsrf-token was not set
			// $http({
			// 	method: api.type,
			// 	data: api.data,
			// 	url: api.url
			// }).then(function success(response){
			// 	$scope.response = asJSON(response);
			// }, function error(response){
			// 	$scope.response = asJSON(response);
			// });

		};
	});
});