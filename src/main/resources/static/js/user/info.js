/**
 * Info.js (user)
 */

const ENDPOINTS = {
    api: '/api/user/',
    list: '/user/list'
};

$(document).ready(function () {
    infoInit();
});

function infoInit() {
    const userSeq = $('#userSeq').val();
    if ( userSeq ) {
        fn_fetchGetData(`${ENDPOINTS.api}${userSeq}`).then(data => {
            if ( data.result ) {
                if ( data.data != null ) {
                    const result = data.data;
                    $('.userNm').text(result.userNm);
                    $('.userId').text(result.userId);
                    $('.roleNm').text(result.roleNm);
                    $('.activeYn').text(result.activeYn);
                    $('.signupDt').text(result.signupDt);
                    $('.userEmail').text(result.userEmail != null ? result.userEmail : '없음');

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

function restConfirm() {

    $('#confirmModal .modal-body').text($('#restConfirm').val());
    $('#confirmModal .saveBtn').off('click').on('click', pwdReset);
    $('#confirmModal').modal('show');
}

function pwdReset() {

}