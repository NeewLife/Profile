$(document).ready(function(){

	$("#search").on("change", function(){
	
		let searchType = $("#searchType").val();
		let keyword = $("#keyword").val();
		let authType = $("#authType").val();
		let startDate = $("#startDate").val();
		let endDate = $("#endDate").val();
		if(endDate == ''){
		    var now = new Date();
		    endDate = now.getFullYear() + '-' + now.getMonth() + '-' + now.getDate()
		}

		var html = '';
		
		$.ajax({
	        type: 'post', 
	        url: "/search", 
	        dataType: "JSON",
	        data: {"searchType":searchType
	        	 , "keyword":keyword
	        	 , "authType":authType
	        	 , "startDate":startDate
	        	 , "endDate":endDate}, // 원하는 값을 중복확인하기위해서  JSON 형태로 DATA 전송
	        success: function(data){
	        	$("#posts").empty();
	        	if(data.length>=1){
				data.forEach(function(item){
	        		html += `
	        				<tr onclick="location.href='/view?postId=${item.postId}'" style="cursor:pointer">
								<td>${item.postId}</td>
								<td>${item.writer}</td>
								<td>${item.title}</td>
								<td>${item.writeDate}</td>
								<td>${item.confirmDate}</td>
							`
							if(item.proxyConfirmPerson != null){
							    html += `<td>${item.confirmPerson}(${item.proxyConfirmPerson})</td>`
							}else{
							    html += `<td>${item.confirmPerson}</td>`
							}
						switch(item.confirmStatus){
							case 'TEM':
								html += `<td>임시저장</td></tr>`
								break;
							case 'WAIT':
								html += `<td>결재대기</td></tr>`
								break;
							case 'ING':
								html += `<td>결재중</td></tr>`
								break;
							case 'FIN':
								html += `<td>결재완료</td></tr>`
								break;
							case 'REJ':
								html += `<td>반려</td></tr>`
								break;
						}
	        		})
        		}
        		else{
        		    html += `<div class="listIsEmpty">검색된 내용이 없습니다</div>`
        		}
        		console.log(html);
        		$("#posts").append(html);
	        },
	        error : function(error){alert(error);}
	    });
	});

    // 대리권한 취소했을 시 정보 초기화
	$('.modal').on('hidden.bs.modal', function (e) {
        $(this).find('#giveProxyForm')[0].reset();
    });

    // 대리권한
	$(".proxyBtn").on("click", function(){
		
		$("#recipName").empty();
		var html = "<option value='' selected='selected'>선택</option>";

		$.ajax({
	        type: 'post', 
	        url: "/viewProxy", 
	        dataType: "JSON",
	        data: { userRank:userRank }, // 원하는 값을 중복확인하기위해서  JSON 형태로 DATA 전송
	        success: function(data){
	        	
	        	if(data.length>=1){
				data.forEach(function(item){
					html += `
							<option value=${item.userName} name="recipName">${item.userName}</option>
							`
					});
	        		$("#recipName").append(html);
        		}
        		$("#recipName").on("change", function(){
        			var values = $(this).val();
        			var userData = $.grep(data, function(item) {
					    return item.userName == values;
					});
					$("#recipRankKR").val(userData[0].userRankKR);
					$("#recipRank").val(userData[0].userRank);
					$("#recipId").val(userData[0].id);
        		});
	        }
        });
	});
})

function giveProxy(){
	let selectVal = document.getElementById('recipName');
	let recipName = selectVal.options[selectVal.selectedIndex].value;
	if(selectVal.value == ""){
	    alert("대리결재자를 선택하세요");
	    return false;
	}
	alert("대리결재자 : " + recipName + " 에게 권한이 부여되었습니다");
	document.getElementById('giveProxyForm').submit();
}

function view(postId){
	let url = "/view?postId=";
	location.href = url + postId;
}

