/**
 * List.js (log)
 */

const ELEMENTS = {
    trNone: $('.tr_none').detach(),
    trInfo: $('.tr_info').detach()
}

const ENDPOINTS = {
    info: '/log/info'
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
            infoClone.find('.form-check-input').val(info.logSeq);
            infoClone.find('.rownum').text(info.rownum);
            infoClone.find('.errCd').text(info.errCd);
            infoClone.find('.errLevel').text(info.errLevel);
            const errMsg = info.errMsg;
            infoClone.find('.errMsg').text(errMsg.length > 65 ? errMsg.substring(0, 65) + " ..." : errMsg).attr('href', `${ENDPOINTS.info}?logSeq=${info.logSeq}`);
            infoClone.find('.requestMethod').text(info.requestMethod);
            infoClone.find('.occurredDt').text(info.occurredDt);
            infoClone.find('.resolvedStat').text(info.resolvedStat);

            $('.listFragment').find('tbody').append(infoClone);
        });
    } else {
        $('.listFragment').find('tbody').append(ELEMENTS.trNone.clone());
    }
}