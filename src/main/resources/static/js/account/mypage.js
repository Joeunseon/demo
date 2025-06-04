/**
 * mypage.js (Account)
 */

const ENDPOINTS = {
    me: '/api/user/me',
    verify: '/account/verify-password'
};

$(document).ready(function () {
    meInit();
});

function meInit() {
    if ( $('#accountKey').val() == 'ACCOUNT_VERIFY' ) {
        fn_fetchGetData(`${ENDPOINTS.me}`).then(data => {
            if ( data.result ) {
                if ( data.data != null ) {
                    const result = data.data;
                    $('.userNm').text(result.userNm);
                    $('.userId').text(result.userId);
                    $('.signupDt').text(result.signupDt);
                    $('.userEmail').text(result.userEmail != null ? result.userEmail : '없음');

                    if ( result.profileImg != null && result.profileImg != '' ) {
                        $('.profileImg').attr('src', 'data:image/png;base64,'+result.profileImg);
                    }
                }
            } else {
                $('#alertModal').find('.modal-body').text($('#errorRequest').val());
                $('#alertModal').modal('show');
            }
        });
    } else {
        $('#alertModal').find('.modal-body').text($('#errorRequest').val());
        $('#alertModal').modal('show');
        $('#alertModal').on('hidden.bs.modal', function () {
            location.href = `${ENDPOINTS.verify}`;
        });
    }
}