/**
 * Password.js (Account)
 */

const ENDPOINTS = {
    main: '/',
    delay: '/api/account/password/delay'
};

$(document).on('ready', function () {
    if ( !$('#accountKey').val() ) {
        $('#alertModal').find('.modal-body').text($('#errorRequest').val());
        $('#alertModal').modal('show');
        $('#alertModal').on('hidden.bs.modal', function () {
            location.href = `${ENDPOINTS.main}`;
        });
    }
});

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