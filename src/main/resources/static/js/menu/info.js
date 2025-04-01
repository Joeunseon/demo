/**
 * Info.js (menu)
 */
const ENDPOINTS = {
    info: '/api/menu/'
};

const ELEMENTS = {
    item: $('.accordion-item').detach(),
    itemNone: $('.accordion_none').detach()
}

$(document).ready(function () {
    infoInit();
});

function infoInit() {
    const menuSeq = $('#menuSeq').val();
    if ( menuSeq ) {
        fn_fetchGetData(`${ENDPOINTS.info}${menuSeq}`).then(data => {
            if ( data.result ) {
                if ( data.data != null ) {
                    const result = data.data;
                    $('.menuNm').text(result.menuNm);
                    $('.menuTypeDesc').text(result.menuTypeDesc);
                    $('.menuUrl').text(result.menuUrl != '' ? `(${result.menuUrl})` : '');
                    $('.activeYn').text(result.activeYn);
                    $('.menuOrder').text(result.menuOrder);
                }
            } else {
                $('#alertModal').find('.modal-body').text($('#errorRequest').val());
                $('#alertModal').modal('show');
                $('#alertModal').on('hidden.bs.modal', function () {
                    history.back();
                });
            }
        });
    } else {
        $('#alertModal').find('.modal-body').text($('#errorRequest').val());
        $('#alertModal').modal('show');
        $('#alertModal').on('hidden.bs.modal', function () {
            history.back();
        });
    }
}