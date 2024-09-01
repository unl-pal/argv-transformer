app = angular.module 'PublicApp', [
	'ngRoute'
	'oc.modal'
	'ui.bootstrap'
	'ngCordova'
	'ngMaterial'
]

app.controller 'IndexController', require './index-controller'
# app.factory 'LoginInterceptor', require './login/login-interceptor'

app.config ['$httpProvider','$routeProvider', ($httpProvider,$routeProvider) ->
	# $httpProvider.interceptors.push 'LoginInterceptor'

	$routeProvider.when '/',
		controller : require './home/home'
		templateUrl : 'home/home.html'
	# .when '/contact',
	# 	controller : require './contact/contact'
	# 	templateUrl : '/contact/contact.html'
	# .when '/features',
	# 		controller : require './features/features'
	# 		templateUrl : '/features/features.html'
	# .when '/change-password',
	# 		controller : require './change-password/change-password'
	# 		templateUrl : '/change-password/change-password.html'
	.otherwise
		redirectTo : '/'
]

module.exports = app
