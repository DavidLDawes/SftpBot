import { Routes } from '@angular/router';

import { UserRouteAccessService } from '../../shared';
import { SftpTestCaseComponent } from './sftp-test-case.component';
import { SftpTestCaseDetailComponent } from './sftp-test-case-detail.component';
import { SftpTestCasePopupComponent } from './sftp-test-case-dialog.component';
import { SftpTestCaseDeletePopupComponent } from './sftp-test-case-delete-dialog.component';

export const sftpTestCaseRoute: Routes = [
    {
        path: 'sftp-test-case',
        component: SftpTestCaseComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'SftpTestCases'
        },
        canActivate: [UserRouteAccessService]
    }, {
        path: 'sftp-test-case/:id',
        component: SftpTestCaseDetailComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'SftpTestCases'
        },
        canActivate: [UserRouteAccessService]
    }
];

export const sftpTestCasePopupRoute: Routes = [
    {
        path: 'sftp-test-case-new',
        component: SftpTestCasePopupComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'SftpTestCases'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    },
    {
        path: 'sftp-test-case/:id/edit',
        component: SftpTestCasePopupComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'SftpTestCases'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    },
    {
        path: 'sftp-test-case/:id/delete',
        component: SftpTestCaseDeletePopupComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'SftpTestCases'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    }
];
