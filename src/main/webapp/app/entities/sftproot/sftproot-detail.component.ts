import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { Subscription } from 'rxjs/Subscription';
import { JhiEventManager } from 'ng-jhipster';

import { Sftproot } from './sftproot.model';
import { SftprootService } from './sftproot.service';

@Component({
    selector: 'jhi-sftproot-detail',
    templateUrl: './sftproot-detail.component.html'
})
export class SftprootDetailComponent implements OnInit, OnDestroy {

    sftproot: Sftproot;
    private subscription: Subscription;
    private eventSubscriber: Subscription;

    constructor(
        private eventManager: JhiEventManager,
        private sftprootService: SftprootService,
        private route: ActivatedRoute
    ) {
    }

    ngOnInit() {
        this.subscription = this.route.params.subscribe((params) => {
            this.load(params['id']);
        });
        this.registerChangeInSftproots();
    }

    load(id) {
        this.sftprootService.find(id).subscribe((sftproot) => {
            this.sftproot = sftproot;
        });
    }
    previousState() {
        window.history.back();
    }

    ngOnDestroy() {
        this.subscription.unsubscribe();
        this.eventManager.destroy(this.eventSubscriber);
    }

    registerChangeInSftproots() {
        this.eventSubscriber = this.eventManager.subscribe(
            'sftprootListModification',
            (response) => this.load(this.sftproot.id)
        );
    }
}
