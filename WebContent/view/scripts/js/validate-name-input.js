(function() {
	var $nameInput = $('.name');
	var $submitButton = $(':submit');

	var defaultBorderStyle = $nameInput.css('border');
	
	var checkIfValid = function() {
		var isNameLongEnough = $nameInput.val().length > 2;
			
		if (!isNameLongEnough) {
			$nameInput.css('border', '2px solid red');
			$submitButton.attr('disabled', 'disabled');
		} else {
			$nameInput.css('border', defaultBorderStyle);
			if ($submitButton.attr('disabled') !== undefined) {
				$submitButton.removeAttr('disabled');
			}
		}
	};
	
	$nameInput.on('input', function() {
		checkIfValid();
	});
	
	checkIfValid();
}());