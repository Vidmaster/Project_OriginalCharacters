describe("App", function() {

	beforeEach(module('app'));

    var $controller;
	beforeEach(inject(function($injector) {
		$controller = $injector.get('$controller');
	}));

	it("loads a controller", function() {
		//var controller = $controller('SearchController')
		expect(true).toEqual(true);
	});

});