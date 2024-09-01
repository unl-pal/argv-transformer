ctr = module.exports = ($scope,$location,$ocModal,$http,$modal) ->
	###
	## sourceURL=hello.js
	###
	# $scope.getLogin = ()->
	# 	$http.get "/rest/getLogin"
	# 	.success (data,status,headers,config)->
	# 		$scope.user = data.user
	# 		$scope.checked = true

	# $scope.getLogin()

	$scope.goto = (path)->
		$scope.path = path
		$location.path(path)

	# $scope.login = ()->
	#
	# 	modalInstance = $modal.open
	# 		templateUrl : 'login/login.html'
	# 		controller : require './login/login'
	#
	# 	modalInstance.result.then (result)->
	# 		$scope.user = result
	#
	# $scope.signup = ()->
	# 	modal = $modal.open
	# 		templateUrl: 'signup/signup.html'
	# 		controller: require './signup/signup'
	# 	# $ocModal.open
	# 	# 	id: 'modal1',
	# 	# 	url: 'signup/signup.html'
	# 	# 	controller: 'SignupController'
	#
	# $scope.pay = (description,amount)->
	# 	$modal.open
	# 		templateUrl: 'payment/payment.html'
	# 		controller: require './payment/payment'
	#
	# $scope.logout = ()->
	# 	$http.post "/rest/logout"
	# 	.success (data,status,headers,config)-> delete $scope.user

ctr.$inject = [ '$scope','$location','$ocModal','$http','$modal']
