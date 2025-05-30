/**
 * verify.js (Account)
 */

const ENDPOINTS = {
    me: '/account/mypage',
    verify: '/api/account/password/verify'
};

const form = document.getElementById('accountForm');

$(document).ready(function () {
    $('#userPwd').on('keyup', function(key) {
        if ( key.keyCode == 13 )
			accountValidity();
    });
});

function accountValidity() {
    if (!form.checkValidity()) {
        form.reportValidity();
        return;
    }

    account();
}

function account() {
    const data = Object.fromEntries(new URLSearchParams($(form).serialize()));

    fn_fetchPostData(ENDPOINTS.verify, data)
        .then(data => {
            if ( data.result ) {
                location.href = `${ENDPOINTS.me}?accountKey=${data.data}`;
            } else {
                $('#alertModal').find('.modal-body').text(data.message);
                $('#alertModal').modal('show');
            }
        });
}