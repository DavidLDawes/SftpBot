import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { Response } from '@angular/http';

import { Observable } from 'rxjs/Observable';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager, JhiAlertService } from 'ng-jhipster';

import { SftpTestCase } from './sftp-test-case.model';
import { SftpTestCasePopupService } from './sftp-test-case-popup.service';
import { SftpTestCaseService } from './sftp-test-case.service';
import { Sftproot, SftprootService } from '../sftproot';
import { ResponseWrapper } from '../../shared';

@Component({
    selector: 'jhi-sftp-test-case-dialog',
    templateUrl: './sftp-test-case-dialog.component.html'
})
export class SftpTestCaseDialogComponent implements OnInit {

    sftpTestCase: SftpTestCase;
    isSaving: boolean;

    sftproots: Sftproot[];

    constructor(
        public activeModal: NgbActiveModal,
        private jhiAlertService: JhiAlertService,
        private sftpTestCaseService: SftpTestCaseService,
        private sftprootService: SftprootService,
        private eventManager: JhiEventManager
    ) {
    }

    ngOnInit() {
        this.isSaving = false;
        this.sftprootService.query()
            .subscribe((res: ResponseWrapper) => { this.sftproots = res.json; }, (res: ResponseWrapper) => this.onError(res.json));
    }

    clear() {
        this.activeModal.dismiss('cancel');
    }

    save() {
        this.isSaving = true;
        if (this.sftpTestCase.id !== undefined) {
            this.subscribeToSaveResponse(
                this.sftpTestCaseService.update(this.sftpTestCase));
        } else {
            this.subscribeToSaveResponse(
                this.sftpTestCaseService.create(this.sftpTestCase));
        }
    }

    private subscribeToSaveResponse(result: Observable<SftpTestCase>) {
        result.subscribe((res: SftpTestCase) =>
            this.onSaveSuccess(res), (res: Response) => this.onSaveError());
    }

    private onSaveSuccess(result: SftpTestCase) {
        this.eventManager.broadcast({ name: 'sftpTestCaseListModification', content: 'OK'});
        this.isSaving = false;
        this.activeModal.dismiss(result);
    }

    private onSaveError() {
        this.isSaving = false;
    }

    private onError(error: any) {
        this.jhiAlertService.error(error.message, null, null);
    }

    trackSftprootById(index: number, item: Sftproot) {
        return item.id;
    }
}

@Component({
    selector: 'jhi-sftp-test-case-popup',
    template: ''
})
export class SftpTestCasePopupComponent implements OnInit, OnDestroy {

    routeSub: any;

    constructor(
        private route: ActivatedRoute,
        private sftpTestCasePopupService: SftpTestCasePopupService
    ) {}

    ngOnInit() {
        this.routeSub = this.route.params.subscribe((params) => {
            if ( params['id'] ) {
                this.sftpTestCasePopupService
                    .open(SftpTestCaseDialogComponent as Component, params['id']);
            } else {
                this.sftpTestCasePopupService
                    .open(SftpTestCaseDialogComponent as Component);
            }
        });
    }

    ngOnDestroy() {
        this.routeSub.unsubscribe();
    }
}
