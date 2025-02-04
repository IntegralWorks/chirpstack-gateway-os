DESCRIPTION = "Driver/HAL to build a gateway using a concentrator board based on Semtech SX1302"
HOMEPAGE = "https://github.com/Lora-net/sx1302_hal"
PRIORITY = "optional"
LICENSE = "BSD"
LIC_FILES_CHKSUM = "file://LICENSE.TXT;md5=d2119120bd616e725f4580070bd9ee19"
PR = "r2"
PRR = "r2"
RDEPENDS:${PN}-utils = "dfu-util"

SRCREV = "cb2aac3f2d14e55366bb59586c21ab32b0a03a7e"
SRC_URI = "\
    git://github.com/brocaar/sx1302_hal.git;protocol=https;branch=master \
    file://library.cfg \
"

S = "${WORKDIR}/git"

# Use clang as we will be linking against this library using Rust. With the
# default gcc we get link errors.
TOOLCHAIN = "clang"

CFLAGS += "-I inc -I ../libtools/inc"

do_configure:append() {
    cp ${WORKDIR}/library.cfg ${S}/libloragw/library.cfg
}

do_compile() {
    oe_runmake
}

do_install() {
    install -d ${D}${libdir}/libloragw-sx1302
    install -d ${D}${includedir}/libloragw-sx1302

    install -m 0644 libtools/*.a ${D}${libdir}
    install -m 0644 libtools/inc/* ${D}${includedir}

    install -m 0644 libloragw/libloragw.a ${D}${libdir}/libloragw-sx1302.a
    install -m 0644 libloragw/inc/* ${D}${includedir}/libloragw-sx1302

    install -d ${D}/opt/libloragw-sx1302/gateway-utils
    install -d ${D}/opt/libloragw-sx1302/mcu_bin
    install -m 0755 util_boot/boot ${D}/opt/libloragw-sx1302/gateway-utils/
    install -m 0644 mcu_bin/* ${D}/opt/libloragw-sx1302/mcu_bin/
}

PACKAGES += "${PN}-utils"

FILES:${PN} = "${libdir}"
FILES:${PN}-staticdev = "${libdir}"
FILES:${PN}-dev = "${includedir}"
FILES:${PN}-utils = "/opt/libloragw-sx1302/gateway-utils/* /opt/libloragw-sx1302/mcu_bin/*"

INSANE_SKIP:${PN}-utils = "ldflags"
