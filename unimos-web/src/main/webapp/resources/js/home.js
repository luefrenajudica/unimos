/* * SYSTEMTECH INTEGRAL - home.js
 * Versión Final: Corrección de Menú y Estabilidad de Overlays
 */

// 1. GESTIÓN DE AJAX Y CARGA
$(document).on("ajaxStart pfAjaxSend", function () {
    $("html").addClass("progress");
    $("#overlay").css("display", "block");
}).on("ajaxStop pfAjaxComplete", function () {
    $("html").removeClass("progress");
    $("#overlay").css("display", "none");
    
    // CORRECCIÓN DE DESPLAZAMIENTO POST-AJAX
    // En lugar de sobrescribir el componente, forzamos el recalculo de posición
    // de todos los paneles que se hayan podido desorientar (0,0)
    setTimeout(function() {
        if (window.PrimeFaces) {
            $('.ui-selectonemenu-panel, .ui-selectcheckboxmenu-panel').each(function() {
                var panel = $(this);
                // Si el panel está en el top 0 (descuadrado), lo ocultamos para que 
                // al siguiente clic PrimeFaces lo recalcule desde la posición del padre.
                if (panel.offset().top < 10) {
                    panel.hide();
                }
            });
        }
    }, 100);
});

function do_nothing() {
    return false;
}

function disableF5(e) {
    if ((e.which || e.keyCode) === 116) {
        // e.preventDefault();
    }
}

// 2. FUNCIÓN DE TOGGLE PARA EL MENÚ LATERAL
// Usamos matchMedia para evitar el error del "if" que mencionaste
function toggleMenu() {
    const sidebar = document.getElementById('sidebar');
    const content = document.getElementById('content');
    const isMobile = window.matchMedia("(max-width: 991px)").matches;

    if (isMobile) {
        // En móvil usamos sidebar-active
        if (sidebar) sidebar.classList.toggle('sidebar-active');
    } else {
        // En escritorio usamos sidebar-collapsed
        if (sidebar) sidebar.classList.toggle('sidebar-collapsed');
        if (content) content.classList.toggle('content-expanded');
    }
}

$(document).ready(function () {
    $(document).on("keydown", disableF5);

    // VINCULACIÓN ROBUSTA DEL BOTÓN DE MENÚ
    // Usamos delegación para que nunca se bloquee tras un Ajax
    $(document).on('click', '.boton-menu-selector', function(e) {
        e.preventDefault();
        toggleMenu();
    });

    // --- LÓGICA DE RESALTADO DE MENÚ (ORIGINAL) ---
    setTimeout(function() {
        var currentPath = window.location.pathname;
        var menuLinks = document.querySelectorAll('.ui-menuitem-link');
        var allHeaders = document.querySelectorAll('.ui-panelmenu-header');
        var allContents = document.querySelectorAll('.ui-panelmenu-content');

        // Reset
        allContents.forEach(function(content) { content.style.display = 'none'; });
        allHeaders.forEach(function(header) {
            header.classList.remove('ui-state-active');
            var icon = header.querySelector('.ui-panelmenu-icon');
            if (icon) icon.classList.replace('pi-chevron-down', 'pi-chevron-right');
        });

        var linkEncontrado = false;
        menuLinks.forEach(function(link) {
            var href = link.getAttribute('href');
            if (href && currentPath.indexOf(href) !== -1 && !linkEncontrado) {
                linkEncontrado = true;
                link.parentElement.classList.add('menu-item-active');
                var parentContent = link.closest('.ui-panelmenu-content');
                if (parentContent) {
                    parentContent.style.display = 'block';
                    var header = parentContent.previousElementSibling;
                    if (header && header.classList.contains('ui-panelmenu-header')) {
                        header.classList.add('ui-state-active');
                        var icon = header.querySelector('.ui-panelmenu-icon');
                        if (icon) icon.classList.replace('pi-chevron-right', 'pi-chevron-down');
                    }
                }
            }
        });
    }, 50); 
});