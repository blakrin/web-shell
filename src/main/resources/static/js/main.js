$(document).ready(function() {
 $("#cmd-form").submit(function (event) {
		
        //stop submit the form, we will post it manually.
        event.preventDefault();
        
         var cmd = $("#command").val();
         
         if(cmd === 'clean') {
			$('#command-response').empty();
		 }else {
			cmd_ajax();	
		}
    });
     

});

function cmd_ajax() {
	 var cmd = $("#command").val();

	$.ajax({
		type: "POST",
		contentType: "application/json",
		url: "/run-cmd",
		data: JSON.stringify(cmd),
		dataType: 'json',
		cache: false,
		timeout: 6000,
		complete : function(jqXHR) {
			if(jqXHR.readyState === 4) {
          	$('#command-response').append(jqXHR.responseText);
          		console.log("ERROR : ", jqXHR);
       		}   
		},
		error: function(e) {
			//$('#command-response').append(e.responseText);
			console.log("ERROR : ", e);

		}
	});

}