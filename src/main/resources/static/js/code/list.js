/**
 * List.js (code)
 */

const ELEMENTS = {
    trNone: $('.tr_none').detach(),
    trInfo: $('.tr_info').detach()
}

const ENDPOINTS = {
    info: '/code/info'
}

$(document).ready(function () {
    listInit();
    
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
    $('#page').val(1);
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
            infoClone.find('.grpCd').text(info.grpCd).attr('href', `${ENDPOINTS.info}?grpSeq=${info.grpSeq}`);
            infoClone.find('.grpNm').text(info.grpNm).attr('href', `${ENDPOINTS.info}?grpSeq=${info.grpSeq}`);
            if ( info.cdSeq ) {
                infoClone.find('.cd').text(info.cd).attr('href', `${ENDPOINTS.info}?cdSeq=${info.cdSeq}`);
                infoClone.find('.cdNm').text(info.cdNm).attr('href', `${ENDPOINTS.info}?cdSeq=${info.cdSeq}`);
                infoClone.find('.useYn').text(info.codeUseYn);
            } else {
                infoClone.find('.cd').attr('href', 'javascript:void(0);');
                infoClone.find('.cdNm').attr('href', 'javascript:void(0);');
                infoClone.find('.useYn').text(info.grpUseYn);
            }

            $('.listFragment').find('tbody').append(infoClone);
        });
    } else {
        $('.listFragment').find('tbody').append(ELEMENTS.trNone.clone());
    }
}