# Recipe created by recipetool
# This is the basis of a recipe and may need further editing in order to be fully functional.
# (Feel free to remove these comments when editing.)

# WARNING: the following LICENSE and LIC_FILES_CHKSUM values are best guesses - it is
# your responsibility to verify that the values are complete and correct.
#
# The following license files were not able to be identified and are
# represented as "Unknown" below, you will need to check them yourself:
#   LICENSE
LICENSE = "Unknown"
LIC_FILES_CHKSUM = "file://LICENSE;md5=f098732a73b5f6f3430472f5b094ffdb"

SRC_URI = "git://git@github.com/cu-ecen-aeld/assignment-7-serges147.git;protocol=ssh;branch=main \
           file://0001-fix-makefile.patch \
           file://scull-start-stop \
           file://scull_load \
           file://scull_unload \
           "

# Modify these as desired
PV = "1.0+git${SRCPV}"
SRCREV = "e3330d4a957158197d47a91ed0a1864c97a73c26"

S = "${WORKDIR}/git"

inherit update-rc.d

INITSCRIPT_PACKAGES = "${PN}"
INITSCRIPT_NAME:${PN} = "scull-start-stop"

inherit module

FILES:${PN} += "${bindir}/scull_load"
FILES:${PN} += "${bindir}/scull_unload"
FILES:${PN} += "${sysconfdir}/init.d/scull-start-stop"

EXTRA_OEMAKE:append:task-install = " -C ${STAGING_KERNEL_DIR} M=${S}/scull"
EXTRA_OEMAKE += "KERNELDIR=${STAGING_KERNEL_DIR}"

do_install:append() {
	install -d ${D}${sysconfdir}/init.d
	install -m 0755 ${WORKDIR}/scull-start-stop ${D}${sysconfdir}/init.d

	install -d ${D}${bindir}
	install -m 0755 ${WORKDIR}/scull_load ${D}${bindir}/
	install -m 0755 ${WORKDIR}/scull_unload ${D}${bindir}/
}
