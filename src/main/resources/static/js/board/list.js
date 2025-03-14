/**
 * List.js (board)
 */

const ELEMENTS = {
    trNone: $('.tr_none').detach(),
    trInfo: $('.tr_info').detach()
}

const ENDPOINTS = {
    info: '/board/info'
}

$(document).ready(function () {
    listInit();
    
    $('#searchStartDt, #searchEndDt').on('change', function() {
        let startDt = $('#searchStartDt').val();
		let endDt = $('#searchEndDt').val();
        
        if ( startDt && endDt ) {
            startDt = Number(startDt.replace(/-/gi,""));
            endDt = Number(endDt.replace(/-/gi,""));
            
            if ( startDt > endDt ) {
                $('#alertModal').find('.modal-body').text($('#dateValidation').val());
                $('#alertModal').modal('show');
                $(this).val('');
            }
        }
	});
    
    $('#searchKeyword').on('keyup', function(key) {
        if ( key.keyCode == 13 )
			searchList();
    });
    
    $(document).on('click', '[data-role="btnGoPage"]', function (e) {
        let page = $(this).data('page');
        
        if ( $('#page').val() != page ) {
            $('#page').val(page);
            listInit();
        }
    });
});

function listInit() {
    fn_resultList('searchForm', drawResultList);
}

function searchList() {
    $('#page').val(1);
    fn_resultList('searchForm', drawResultList);
}

function drawResultList(data) {
    $('.listFragment').find('tbody').empty();
    $('.listFragment').find('.totalCnt').text(data.totalCnt);
    $('#page').val(data.currentPage);

    if ( data.totalCnt > 0 ) {
        data.resultList.forEach(info => {
            let infoClone =  ELEMENTS.trInfo.clone();
            infoClone.find('.rownum').text(info.rownum);
            infoClone.find('.title').text(info.title).attr('href', `${ENDPOINTS.info}?boardSeq=${info.boardSeq}`);
            infoClone.find('.writerNm').text(info.writerNm);
            infoClone.find('.regDt').text(info.regDt);
            infoClone.find('.viewCnt').text(info.viewCnt);

            $('.listFragment').find('tbody').append(infoClone);
        });
    } else {
        $('.listFragment').find('tbody').append(ELEMENTS.trNone.clone());
    }
}