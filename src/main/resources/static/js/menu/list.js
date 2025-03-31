/**
 * List.js (menu)
 */

const ELEMENTS = {
    trNone: $('.tr_none').detach(),
    trInfo: $('.tr_info').detach()
}

const ENDPOINTS = {
    info: '/menu/info'
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
            infoClone.find('.menuNm').text(info.menuNm).attr('href', `${ENDPOINTS.info}?menuSeq=${info.menuSeq}`);
            infoClone.find('.menuUrl').text(info.menuUrl);
            infoClone.find('.activeYn').text(info.activeYn);
            infoClone.find('.menuOrder').text(info.menuOrder);

            $('.listFragment').find('tbody').append(infoClone);
        });
    } else {
        $('.listFragment').find('tbody').append(ELEMENTS.trNone.clone());
    }
}