import { Routes } from '@angular/router';

import { UserRouteAccessService } from '../../shared';
import { SftprootComponent } from './sftproot.component';
import { SftprootDetailComponent } from './sftproot-detail.component';
import { SftprootPopupComponent } from './sftproot-dialog.component';
import { SftprootDeletePopupComponent } from './sftproot-delete-dialog.component';

export const sftprootRoute: Routes = [
    {
        path: 'sftproot',
        component: SftprootComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'Sftproots'
        },
        canActivate: [UserRouteAccessService]
    }, {
        path: 'sftproot/:id',
        component: SftprootDetailComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'Sftproots'
        },
        canActivate: [UserRouteAccessService]
    }
];

export const sftprootPopupRoute: Routes = [
    {
        path: 'sftproot-new',
        component: SftprootPopupComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'Sftproots'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    },
    {
        path: 'sftproot/:id/edit',
        component: SftprootPopupComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'Sftproots'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    },
    {
        path: 'sftproot/:id/delete',
        component: SftprootDeletePopupComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'Sftproots'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    }
];
