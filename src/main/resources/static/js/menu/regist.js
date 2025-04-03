/**
 * Regist.js (menu)
 */
const ENDPOINTS = {
    tree: '/api/menus/tree',
    check: '/api/menu/check-duplicate',
    save: '/api/menu',
    info: '/menu/info'
}

const ELEMENTS = {
    liInfo: $('#menuTree').find('li').detach()
}

const form = document.getElementById('menuForm');

$(document).ready(function () {
    treeInit();

    $('#menuNm').on('focusout', function () {
        $('#checkMenuNmBtn').data('flag', false);
    });

    $('#menuUrl').on('focusout', function () {
        $('#checkMenuUrlBtn').data('flag', false);
    });
});

function treeInit() {

    fn_fetchGetData(ENDPOINTS.tree).then(data => {
        if ( data.result ) {
            if ( data.data != null ) {
                const buildTree = (list, parent) => {
                    list.forEach(item => {
                        const $node = ELEMENTS.liInfo.clone();
                
                        const $btn = $node.find('.menu-select-btn');
                        $btn.text(item.menuNm);
                        $btn.on('click', () => {
                            $('#parentSeq').val(item.menuSeq);
                            $('#parentSeqLabel').text(`${item.menuNm} (선택됨)`);
                            const modal = bootstrap.Modal.getInstance(document.getElementById('menuTreeModal'));
                            modal.hide();
                        });
                
                        if (item.children && item.children.length > 0) {
                            const $childUl = $node.find('.child-list');
                            buildTree(item.children, $childUl);
                        }
                
                        parent.append($node);
                    });
                };
              
                buildTree(data.data, $('#menuTree'));
            }
        } else {
            $('#alertModal').find('.modal-body').text( data.message );
            $('#alertModal').modal('show');
            $('#alertModal').on('hidden.bs.modal', function () {
                history.back();
            });
        }
    });
}

function checkDuplicate(id, el) {
    if ( !$('#'+id).val() ) {
        $('#alertModal .modal-body').text($('#validation').val().replace('{0}', $(`label[for=${id}]`).text()));
        $('#alertModal').modal('show');
        return;
    }
        
    fn_fetchGetData(`${ENDPOINTS.check}?${id}=${$('#'+id).val()}`)
        .then(data => {
            $('#alertModal .modal-body').text(data.message);
            $('#alertModal').modal('show');

            $(el).data('flag', data.result);
        });
}

function menuValidity() {

    if ( $('#menuType').val() != 'MENU' && $('#menuType').val() != 'TOOL' ) {
        $('#menuUrl').attr('required', 'required');
    } else {
        $('#menuUrl').removeAttr('required');
    }

    if (!form.checkValidity()) {
        form.reportValidity();
        return;
    }

    if ( !$('#checkMenuNmBtn').data('flag') ) {
        $('#alertModal').find('.modal-body').text($('#duplication').val().replace('{0}', '메뉴 이름'));
        $('#alertModal').modal('show');
        return;
    }

    if ( !$('#parentSeq').val() ) {
        $('#alertModal').find('.modal-body').text($('#validation').val().replace('{0}', '상위 메뉴').replace('입력', '선택'));
        $('#alertModal').modal('show');
        return;
    }

    if ( ($('#menuUrl').attr('required') !== undefined || $('#menuUrl').val()) && !$('#checkMenuUrlBtn').data('flag') )  {
        $('#alertModal').find('.modal-body').text($('#duplication').val().replace('{0}', '메뉴 URL'));
        $('#alertModal').modal('show');
        return;
    }

    menuConfrim();
}

function menuConfrim() {
    const confirmMsg = $('#saveConfrimMsg').val();

    if ( confirmMsg && confirmMsg.trime !== '' ) {

        $('#confirmModal .modal-body').text(confirmMsg);
        $('#confirmModal .saveBtn').off('click').on('click', menu);
        $('#confirmModal').modal('show');
    }
}

function menu() {
    const data = Object.fromEntries(new URLSearchParams($(form).serialize()));

    fn_fetchPostData(ENDPOINTS.save, data)
        .then(data => {
            $('#alertModal').find('.modal-body').text(data.message);
            $('#alertModal').modal('show');
            
            if ( data.result ) {
                $('#alertModal').on('hidden.bs.modal', function () {
                    location.href = `${ENDPOINTS.info}?menuSeq=${data.data}`;
                });
            }
        });
}