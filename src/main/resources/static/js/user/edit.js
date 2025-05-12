/**
 * Edit.js (user)
 */

const ENDPOINTS = {
    info: '/api/user/'
};

$(document).ready(function () {
    setTimeout(function() {
        infoInit();
    }, 200);
});

function infoInit() {
    const userSeq = $('#userSeq').val();
    if ( userSeq ) {
        fn_fetchGetData(`${ENDPOINTS.info}${userSeq}`).then(data => {
            if ( data.result ) {
                if ( data.data != null ) {
                    const result = data.data;
                    $('.userNm').text(result.userNm);
                    $('.userId').text(result.userId);
                    $('#roleSeq').val(result.roleSeq);
                    $('#active'+result.activeYn).attr('checked', true);
                    $('#userEmail').val(result.userEmail != null ? result.userEmail : '');

                    if ( result.profileImg != null && result.profileImg != '' ) {
                        $('.profileImg').attr('src', 'data:image/png;base64,'+result.profileImg);
                    }
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

function userConfrim() {
    const confirmMsg = $('#saveConfrimMsg').val();

    if ( confirmMsg && confirmMsg.trime !== '' ) {

        $('#confirmModal .modal-body').text(confirmMsg);
        $('#confirmModal .saveBtn').off('click').on('click', user);
        $('#confirmModal').modal('show');
    }
}

function user() {

}