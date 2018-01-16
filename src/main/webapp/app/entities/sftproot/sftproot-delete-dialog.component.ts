import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { Sftproot } from './sftproot.model';
import { SftprootPopupService } from './sftproot-popup.service';
import { SftprootService } from './sftproot.service';

@Component({
    selector: 'jhi-sftproot-delete-dialog',
    templateUrl: './sftproot-delete-dialog.component.html'
})
export class SftprootDeleteDialogComponent {

    sftproot: Sftproot;

    constructor(
        private sftprootService: SftprootService,
        public activeModal: NgbActiveModal,
        private eventManager: JhiEventManager
    ) {
    }

    clear() {
        this.activeModal.dismiss('cancel');
    }

    confirmDelete(id: number) {
        this.sftprootService.delete(id).subscribe((response) => {
            this.eventManager.broadcast({
                name: 'sftprootListModification',
                content: 'Deleted an sftproot'
            });
            this.activeModal.dismiss(true);
        });
    }
}

@Component({
    selector: 'jhi-sftproot-delete-popup',
    template: ''
})
export class SftprootDeletePopupComponent implements OnInit, OnDestroy {

    routeSub: any;

    constructor(
        private route: ActivatedRoute,
        private sftprootPopupService: SftprootPopupService
    ) {}

    ngOnInit() {
        this.routeSub = this.route.params.subscribe((params) => {
            this.sftprootPopupService
                .open(SftprootDeleteDialogComponent as Component, params['id']);
        });
    }

    ngOnDestroy() {
        this.routeSub.unsubscribe();
    }
}
