function goViewPage(seq) {
	location.href = '/post/view?seq=' + seq;
}

function goPage(num){
	$("#pageNum").val(num);
	$(".bt_search").click();
}

$(document).ready(function() {

	$(".bt_search").on("click", function (){
	
		$.ajax({
			type: "post"
		  , url: "page"
		  , data: $("#searchForm").serialize()
    	  , error: function() {
		      console.log('검색실패!!');
		    }
	      , success: function(data) {
	      		console.log(data);
	      		var listElement = $("#list");
	      		let html = "";
				
				listElement.empty();
	      		listElement.html(data);

	        }
		});
	});
	
});