/**
 * Edit.js (Account)
 */

const ENDPOINTS = {
    api: '/api/user/me',
    me: '/account/mypage',
    verify: '/account/verify-password'
};

const form = document.getElementById('accountForm');

$(document).ready(function () {
    setTimeout(function() {
        meInit();
    }, 200);

    // 파일 이벤트
    $('#profileFile').on('change', function () {
        const file = this.files[0];
        const $profileImg = $("#profile");

        if ( file ) {
            const fileType = file.type;
            if ( fileType.startsWith('image/') ) {
                const reader = new FileReader();

                reader.onload = function(e) {
                    $profileImg.attr("src", e.target.result);

                    // base64 변환
                    const base64Data = e.target.result.split(",")[1];
                    $('#profileImg').val(base64Data);
                };

                reader.readAsDataURL(file);
            } else {
                $(this).val('');
                $('#profileImg').val('');
                $profileImg.attr("src", "/images/img-basic-user.png");
                $('#alertModal').find('.modal-body').text('이미지 파일만 업로드할 수 있습니다.');
                $('#alertModal').modal('show');
            }
        }
    });
});

function meInit() {
    if ( $('#accountKey').val() == 'ACCOUNT_VERIFY' ) {
        fn_fetchGetData(`${ENDPOINTS.api}`).then(data => {
            if ( data.result ) {
                if ( data.data != null ) {
                    const result = data.data;
                    $('.userNm').text(result.userNm);
                    $('.userId').text(result.userId);
                    $('#userEmail').val(result.userEmail != null ? result.userEmail : '');

                    if ( result.profileImg != null && result.profileImg != '' ) {
                        $('.profileImg').attr('src', 'data:image/png;base64,'+result.profileImg);
                        $('#profileImg').val(result.profileImg);
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

function accountConfrim() {
    const confirmMsg = $('#saveConfrimMsg').val();

    if ( confirmMsg && confirmMsg.trime !== '' ) {

        $('#confirmModal .modal-body').text(confirmMsg);
        $('#confirmModal .saveBtn').off('click').on('click', account);
        $('#confirmModal').modal('show');
    }
}

function account() {
    const data = Object.fromEntries(new URLSearchParams($(form).serialize()));

    fn_fetchPatchData(`${ENDPOINTS.api}`, data)
        .then(data => {
            $('#alertModal').find('.modal-body').text(data.message);
            $('#alertModal').modal('show');
            
            if ( data.result ) {
                $('#alertModal').on('hidden.bs.modal', function () {
                    location.href = `${ENDPOINTS.me}`;
                });
            }
        });
}