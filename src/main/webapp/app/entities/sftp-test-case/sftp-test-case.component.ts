import { Component, OnInit, OnDestroy } from '@angular/core';
import { Subscription } from 'rxjs/Subscription';
import { JhiEventManager, JhiAlertService } from 'ng-jhipster';

import { SftpTestCase } from './sftp-test-case.model';
import { SftpTestCaseService } from './sftp-test-case.service';
import { Principal, ResponseWrapper } from '../../shared';

@Component({
    selector: 'jhi-sftp-test-case',
    templateUrl: './sftp-test-case.component.html'
})
export class SftpTestCaseComponent implements OnInit, OnDestroy {
sftpTestCases: SftpTestCase[];
    currentAccount: any;
    eventSubscriber: Subscription;

    constructor(
        private sftpTestCaseService: SftpTestCaseService,
        private jhiAlertService: JhiAlertService,
        private eventManager: JhiEventManager,
        private principal: Principal
    ) {
    }

    loadAll() {
        this.sftpTestCaseService.query().subscribe(
            (res: ResponseWrapper) => {
                this.sftpTestCases = res.json;
            },
            (res: ResponseWrapper) => this.onError(res.json)
        );
    }
    ngOnInit() {
        this.loadAll();
        this.principal.identity().then((account) => {
            this.currentAccount = account;
        });
        this.registerChangeInSftpTestCases();
    }

    ngOnDestroy() {
        this.eventManager.destroy(this.eventSubscriber);
    }

    trackId(index: number, item: SftpTestCase) {
        return item.id;
    }
    registerChangeInSftpTestCases() {
        this.eventSubscriber = this.eventManager.subscribe('sftpTestCaseListModification', (response) => this.loadAll());
    }

    private onError(error) {
        this.jhiAlertService.error(error.message, null, null);
    }
}
