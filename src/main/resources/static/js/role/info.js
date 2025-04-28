/**
 * Info.js (role)
 */

const ENDPOINTS = {
    api: '/api/role/',
    menuRole: '/api/role/{roleSeq}/menus',
    list: '/role/list'
};

$(document).ready(function () {
    infoInit();
});

function infoInit() {
    const roleSeq = $('#roleSeq').val();
    if ( roleSeq ) {
        fn_fetchGetData(`${ENDPOINTS.api}${roleSeq}`).then(data => {
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

                    getMenus();
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

function getMenus() {
    const roleSeq = $('#roleSeq').val();
    if ( roleSeq ) { 
        const menusUrl = ENDPOINTS.menuRole.replaceAll('{roleSeq}', roleSeq);
        fn_fetchGetData(menusUrl).then(data => {
            if ( data.result ) {
                const tree = document.getElementById('menuTree');
                tree.innerHTML = ''; // 초기화
                if ( data.data != null && data.data.length > 0 ) {
                    renderMenuTree(data.data, tree);
                } else {
                    const li = document.createElement('li');
                    li.className = 'list-group-item';
                    li.textContent = '설정된 메뉴가 없습니다.';
                    tree.appendChild(li);
                }
            }
        });
    }
}

function renderMenuTree(menus, container) {
    menus.forEach(menu => {
        const li = document.createElement('li');
        li.className = 'list-group-item';
        li.textContent = menu.menuNm;

        // 하위 메뉴가 있으면 재귀적으로 처리
        if (menu.children && menu.children.length > 0) {
            const ul = document.createElement('ul');
            ul.className = 'list-group ms-3 mt-1';
            renderMenuTree(menu.children, ul);
            li.appendChild(ul);
        }

        container.appendChild(li);
    });
}

function deleteConfirm() {
    $('#confirmModal .modal-body').html($('#deleteConfirm').val());
    $('#confirmModal .saveBtn').off('click').on('click', softDelete);
    $('#confirmModal').modal('show');
}

function softDelete() {
    const roleSeq = $('#roleSeq').val();
    if ( roleSeq ) { 
        fn_fetchDeleteData(`${ENDPOINTS.api}${roleSeq}`).then(data => {
            $('#alertModal').find('.modal-body').text(data.message);
            $('#alertModal').modal('show');
            
            if ( data.result ) {
                $('#alertModal').on('hidden.bs.modal', function () {
                    location.href = `${ENDPOINTS.list}`;
                });
            }
        });
    }
}