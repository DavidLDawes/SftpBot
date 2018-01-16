import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { Subscription } from 'rxjs/Subscription';
import { JhiEventManager } from 'ng-jhipster';

import { SftpTestCase } from './sftp-test-case.model';
import { SftpTestCaseService } from './sftp-test-case.service';

@Component({
    selector: 'jhi-sftp-test-case-detail',
    templateUrl: './sftp-test-case-detail.component.html'
})
export class SftpTestCaseDetailComponent implements OnInit, OnDestroy {

    sftpTestCase: SftpTestCase;
    private subscription: Subscription;
    private eventSubscriber: Subscription;

    constructor(
        private eventManager: JhiEventManager,
        private sftpTestCaseService: SftpTestCaseService,
        private route: ActivatedRoute
    ) {
    }

    ngOnInit() {
        this.subscription = this.route.params.subscribe((params) => {
            this.load(params['id']);
        });
        this.registerChangeInSftpTestCases();
    }

    load(id) {
        this.sftpTestCaseService.find(id).subscribe((sftpTestCase) => {
            this.sftpTestCase = sftpTestCase;
        });
    }
    previousState() {
        window.history.back();
    }

    ngOnDestroy() {
        this.subscription.unsubscribe();
        this.eventManager.destroy(this.eventSubscriber);
    }

    registerChangeInSftpTestCases() {
        this.eventSubscriber = this.eventManager.subscribe(
            'sftpTestCaseListModification',
            (response) => this.load(this.sftpTestCase.id)
        );
    }
}
