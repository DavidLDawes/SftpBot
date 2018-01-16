import { Component, OnInit, OnDestroy } from '@angular/core';
import { Subscription } from 'rxjs/Subscription';
import { JhiEventManager, JhiAlertService } from 'ng-jhipster';

import { Sftproot } from './sftproot.model';
import { SftprootService } from './sftproot.service';
import { Principal, ResponseWrapper } from '../../shared';

@Component({
    selector: 'jhi-sftproot',
    templateUrl: './sftproot.component.html'
})
export class SftprootComponent implements OnInit, OnDestroy {
sftproots: Sftproot[];
    currentAccount: any;
    eventSubscriber: Subscription;

    constructor(
        private sftprootService: SftprootService,
        private jhiAlertService: JhiAlertService,
        private eventManager: JhiEventManager,
        private principal: Principal
    ) {
    }

    loadAll() {
        this.sftprootService.query().subscribe(
            (res: ResponseWrapper) => {
                this.sftproots = res.json;
            },
            (res: ResponseWrapper) => this.onError(res.json)
        );
    }
    ngOnInit() {
        this.loadAll();
        this.principal.identity().then((account) => {
            this.currentAccount = account;
        });
        this.registerChangeInSftproots();
    }

    ngOnDestroy() {
        this.eventManager.destroy(this.eventSubscriber);
    }

    trackId(index: number, item: Sftproot) {
        return item.id;
    }
    registerChangeInSftproots() {
        this.eventSubscriber = this.eventManager.subscribe('sftprootListModification', (response) => this.loadAll());
    }

    private onError(error) {
        this.jhiAlertService.error(error.message, null, null);
    }
}
