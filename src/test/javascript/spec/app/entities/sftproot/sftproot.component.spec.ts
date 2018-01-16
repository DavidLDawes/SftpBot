/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, async } from '@angular/core/testing';
import { Observable } from 'rxjs/Observable';
import { Headers } from '@angular/http';

import { SftpbotTestModule } from '../../../test.module';
import { SftprootComponent } from '../../../../../../main/webapp/app/entities/sftproot/sftproot.component';
import { SftprootService } from '../../../../../../main/webapp/app/entities/sftproot/sftproot.service';
import { Sftproot } from '../../../../../../main/webapp/app/entities/sftproot/sftproot.model';

describe('Component Tests', () => {

    describe('Sftproot Management Component', () => {
        let comp: SftprootComponent;
        let fixture: ComponentFixture<SftprootComponent>;
        let service: SftprootService;

        beforeEach(async(() => {
            TestBed.configureTestingModule({
                imports: [SftpbotTestModule],
                declarations: [SftprootComponent],
                providers: [
                    SftprootService
                ]
            })
            .overrideTemplate(SftprootComponent, '')
            .compileComponents();
        }));

        beforeEach(() => {
            fixture = TestBed.createComponent(SftprootComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(SftprootService);
        });

        describe('OnInit', () => {
            it('Should call load all on init', () => {
                // GIVEN
                const headers = new Headers();
                headers.append('link', 'link;link');
                spyOn(service, 'query').and.returnValue(Observable.of({
                    json: [new Sftproot(123)],
                    headers
                }));

                // WHEN
                comp.ngOnInit();

                // THEN
                expect(service.query).toHaveBeenCalled();
                expect(comp.sftproots[0]).toEqual(jasmine.objectContaining({id: 123}));
            });
        });
    });

});
