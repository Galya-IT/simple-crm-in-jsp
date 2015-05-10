(function() {
	$('#clients').on('click', '.edit', function() {
		$target = $(this);
		var clientId = $target.data('id');
		
		var urlForClientEdit = '/CRM/AddEdit' + "?clientId=" + clientId;
		window.location = urlForClientEdit;
		
	});

	$('#clients').on('click', '.delete', function() {
		$target = $(this);
		var clientId = $target.data('id');

		var isConfirmed = confirm('Are you sure you want to delete this item?');
		
		if (isConfirmed) {
			var urlForClientDeletion = '/CRM/Delete';
			
			var data = {
				'clientId' : clientId,
				'operation' : 'delete'
			}
			
			$.ajax({
				url : urlForClientDeletion,
				type : 'POST',
				data : data,
				dataType: 'json',
				success : function(resultData, textStatus, jQueryXMLHttpRequest) {
					var rowToDeleteSelector = "tr[data-id='" + clientId + "']";
					$(rowToDeleteSelector).remove();
				},
				error : function(jQueryXMLHttpRequest, textStatus, errorThrown) {
					// TO DO : dom message
					alert("not working!");
				}
			});
		}
	});
}());
