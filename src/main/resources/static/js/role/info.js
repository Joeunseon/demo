/**
 * Info.js (role)
 */

const ENDPOINTS = {
    info: '/api/role/',
};

$(document).ready(function () {
    infoInit();
});

function infoInit() {
    const roleSeq = $('#roleSeq').val();
    if ( roleSeq ) {
        fn_fetchGetData(`${ENDPOINTS.info}${roleSeq}`).then(data => {
            if ( data.result ) {
                if ( data.data != null ) {
                    const result = data.data;
                    $('.roleNm').text(result.roleNm);
                    $('.roleDesc').text(result.roleDesc);
                    $('.delYn').text(result.delYn);
                    $('.regNm').text(result.regNm);
                    $('.regDt').text(result.regDt);
                    $('.updNm').text(result.updNm);
                    $('.updDt').text(result.updDt);
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