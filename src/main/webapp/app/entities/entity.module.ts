import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';

import { SftpbotSftprootModule } from './sftproot/sftproot.module';
import { SftpbotSftpTestCaseModule } from './sftp-test-case/sftp-test-case.module';
/* jhipster-needle-add-entity-module-import - JHipster will add entity modules imports here */

@NgModule({
    imports: [
        SftpbotSftprootModule,
        SftpbotSftpTestCaseModule,
        /* jhipster-needle-add-entity-module - JHipster will add entity modules here */
    ],
    declarations: [],
    entryComponents: [],
    providers: [],
    schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class SftpbotEntityModule {}
