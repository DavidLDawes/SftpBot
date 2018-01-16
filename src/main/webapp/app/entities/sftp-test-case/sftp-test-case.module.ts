import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';
import { RouterModule } from '@angular/router';

import { SftpbotSharedModule } from '../../shared';
import {
    SftpTestCaseService,
    SftpTestCasePopupService,
    SftpTestCaseComponent,
    SftpTestCaseDetailComponent,
    SftpTestCaseDialogComponent,
    SftpTestCasePopupComponent,
    SftpTestCaseDeletePopupComponent,
    SftpTestCaseDeleteDialogComponent,
    sftpTestCaseRoute,
    sftpTestCasePopupRoute,
} from './';

const ENTITY_STATES = [
    ...sftpTestCaseRoute,
    ...sftpTestCasePopupRoute,
];

@NgModule({
    imports: [
        SftpbotSharedModule,
        RouterModule.forChild(ENTITY_STATES)
    ],
    declarations: [
        SftpTestCaseComponent,
        SftpTestCaseDetailComponent,
        SftpTestCaseDialogComponent,
        SftpTestCaseDeleteDialogComponent,
        SftpTestCasePopupComponent,
        SftpTestCaseDeletePopupComponent,
    ],
    entryComponents: [
        SftpTestCaseComponent,
        SftpTestCaseDialogComponent,
        SftpTestCasePopupComponent,
        SftpTestCaseDeleteDialogComponent,
        SftpTestCaseDeletePopupComponent,
    ],
    providers: [
        SftpTestCaseService,
        SftpTestCasePopupService,
    ],
    schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class SftpbotSftpTestCaseModule {}
