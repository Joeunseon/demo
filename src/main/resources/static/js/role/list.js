/**
 * List.js (role)
 */
const ELEMENTS = {
    trNone: $('.tr_none').detach(),
    trInfo: $('.tr_info').detach()
}

const ENDPOINTS = {
    info: '/role/info'
}

$(document).ready(function () {
    listInit();
    
    $('#searchKeyword').on('keydown', function(event) {
        if (event.key === 'Enter' || event.keyCode === 13) {
            event.preventDefault();
            searchList();
        }
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
            infoClone.find('.roleNm').text(info.roleNm).attr('href', `${ENDPOINTS.info}?roleSeq=${info.roleSeq}`);
            infoClone.find('.roleDesc').text(info.roleDesc);
            infoClone.find('.delYn').text(info.delYn);
            infoClone.find('.regNm').text(info.regNm);
            infoClone.find('.regDt').text(info.regDt);

            $('.listFragment').find('tbody').append(infoClone);
        });
    } else {
        $('.listFragment').find('tbody').append(ELEMENTS.trNone.clone());
    }
}