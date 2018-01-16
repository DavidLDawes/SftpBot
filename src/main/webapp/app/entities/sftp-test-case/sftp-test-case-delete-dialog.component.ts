import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { SftpTestCase } from './sftp-test-case.model';
import { SftpTestCasePopupService } from './sftp-test-case-popup.service';
import { SftpTestCaseService } from './sftp-test-case.service';

@Component({
    selector: 'jhi-sftp-test-case-delete-dialog',
    templateUrl: './sftp-test-case-delete-dialog.component.html'
})
export class SftpTestCaseDeleteDialogComponent {

    sftpTestCase: SftpTestCase;

    constructor(
        private sftpTestCaseService: SftpTestCaseService,
        public activeModal: NgbActiveModal,
        private eventManager: JhiEventManager
    ) {
    }

    clear() {
        this.activeModal.dismiss('cancel');
    }

    confirmDelete(id: number) {
        this.sftpTestCaseService.delete(id).subscribe((response) => {
            this.eventManager.broadcast({
                name: 'sftpTestCaseListModification',
                content: 'Deleted an sftpTestCase'
            });
            this.activeModal.dismiss(true);
        });
    }
}

@Component({
    selector: 'jhi-sftp-test-case-delete-popup',
    template: ''
})
export class SftpTestCaseDeletePopupComponent implements OnInit, OnDestroy {

    routeSub: any;

    constructor(
        private route: ActivatedRoute,
        private sftpTestCasePopupService: SftpTestCasePopupService
    ) {}

    ngOnInit() {
        this.routeSub = this.route.params.subscribe((params) => {
            this.sftpTestCasePopupService
                .open(SftpTestCaseDeleteDialogComponent as Component, params['id']);
        });
    }

    ngOnDestroy() {
        this.routeSub.unsubscribe();
    }
}
