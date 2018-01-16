import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { Response } from '@angular/http';

import { Observable } from 'rxjs/Observable';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { Sftproot } from './sftproot.model';
import { SftprootPopupService } from './sftproot-popup.service';
import { SftprootService } from './sftproot.service';

@Component({
    selector: 'jhi-sftproot-dialog',
    templateUrl: './sftproot-dialog.component.html'
})
export class SftprootDialogComponent implements OnInit {

    sftproot: Sftproot;
    isSaving: boolean;

    constructor(
        public activeModal: NgbActiveModal,
        private sftprootService: SftprootService,
        private eventManager: JhiEventManager
    ) {
    }

    ngOnInit() {
        this.isSaving = false;
    }

    clear() {
        this.activeModal.dismiss('cancel');
    }

    save() {
        this.isSaving = true;
        if (this.sftproot.id !== undefined) {
            this.subscribeToSaveResponse(
                this.sftprootService.update(this.sftproot));
        } else {
            this.subscribeToSaveResponse(
                this.sftprootService.create(this.sftproot));
        }
    }

    private subscribeToSaveResponse(result: Observable<Sftproot>) {
        result.subscribe((res: Sftproot) =>
            this.onSaveSuccess(res), (res: Response) => this.onSaveError());
    }

    private onSaveSuccess(result: Sftproot) {
        this.eventManager.broadcast({ name: 'sftprootListModification', content: 'OK'});
        this.isSaving = false;
        this.activeModal.dismiss(result);
    }

    private onSaveError() {
        this.isSaving = false;
    }
}

@Component({
    selector: 'jhi-sftproot-popup',
    template: ''
})
export class SftprootPopupComponent implements OnInit, OnDestroy {

    routeSub: any;

    constructor(
        private route: ActivatedRoute,
        private sftprootPopupService: SftprootPopupService
    ) {}

    ngOnInit() {
        this.routeSub = this.route.params.subscribe((params) => {
            if ( params['id'] ) {
                this.sftprootPopupService
                    .open(SftprootDialogComponent as Component, params['id']);
            } else {
                this.sftprootPopupService
                    .open(SftprootDialogComponent as Component);
            }
        });
    }

    ngOnDestroy() {
        this.routeSub.unsubscribe();
    }
}
