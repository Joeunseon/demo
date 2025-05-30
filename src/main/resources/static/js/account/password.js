/**
 * Password.js (Account)
 */

const ENDPOINTS = {
    main: '/',
    delay: '/api/account/password/delay'
};

function delay() {

    fn_fetchPatchData(`${ENDPOINTS.delay}`, null)
        .then(data => {
            if ( data.result ) {
                location.href = ENDPOINTS.main;
            } else {
                $('#alertModal').find('.modal-body').text(data.message);
                $('#alertModal').modal('show');
            }
        });
}