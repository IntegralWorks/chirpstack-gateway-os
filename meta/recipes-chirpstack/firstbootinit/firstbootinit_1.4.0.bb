DESCRIPTION = "First boot initialization scripts."
HOMEPAGE = "https://www.chirpstack.io/"
LICENSE = "MIT"
LIC_FILES_CHKSUM = "file://${COMMON_LICENSE_DIR}/MIT;md5=0835ade698e0bcf8506ecda2f7b4f302"
RDEPENDS:${PN} = "cfssl"

SRC_URI = " \
    file://firstbootinit.init \
    file://firstbootinit.sh \
    file://ca-config.json \
    file://ca-csr.json \
    file://mqtt-server.json \
"
PR = "r1"

inherit update-rc.d

INITSCRIPT_NAME = "firstbootinit"
INITSCRIPT_PARAMS = "defaults"


do_install() {
    install -d ${D}/usr
    install -d ${D}/usr/bin
    install -m 0755 ${WORKDIR}/firstbootinit.sh ${D}/usr/bin/firstbootinit

    install -d ${D}${sysconfdir}/init.d
    install -m 0755 ${WORKDIR}/firstbootinit.init ${D}/${sysconfdir}/init.d/firstbootinit

    install -d ${D}${sysconfdir}/chirpstack/certs
    install -m 0644 ${WORKDIR}/*.json ${D}${sysconfdir}/chirpstack/certs
}

FILES_${PN} = " \
    /etc/init.d/firstbootinit \
    /usr/bin/firstbootinit \
"
