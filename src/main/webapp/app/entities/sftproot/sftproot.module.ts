import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';
import { RouterModule } from '@angular/router';

import { SftpbotSharedModule } from '../../shared';
import {
    SftprootService,
    SftprootPopupService,
    SftprootComponent,
    SftprootDetailComponent,
    SftprootDialogComponent,
    SftprootPopupComponent,
    SftprootDeletePopupComponent,
    SftprootDeleteDialogComponent,
    sftprootRoute,
    sftprootPopupRoute,
} from './';

const ENTITY_STATES = [
    ...sftprootRoute,
    ...sftprootPopupRoute,
];

@NgModule({
    imports: [
        SftpbotSharedModule,
        RouterModule.forChild(ENTITY_STATES)
    ],
    declarations: [
        SftprootComponent,
        SftprootDetailComponent,
        SftprootDialogComponent,
        SftprootDeleteDialogComponent,
        SftprootPopupComponent,
        SftprootDeletePopupComponent,
    ],
    entryComponents: [
        SftprootComponent,
        SftprootDialogComponent,
        SftprootPopupComponent,
        SftprootDeleteDialogComponent,
        SftprootDeletePopupComponent,
    ],
    providers: [
        SftprootService,
        SftprootPopupService,
    ],
    schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class SftpbotSftprootModule {}
