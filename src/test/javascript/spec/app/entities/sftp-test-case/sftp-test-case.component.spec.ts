/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, async } from '@angular/core/testing';
import { Observable } from 'rxjs/Observable';
import { Headers } from '@angular/http';

import { SftpbotTestModule } from '../../../test.module';
import { SftpTestCaseComponent } from '../../../../../../main/webapp/app/entities/sftp-test-case/sftp-test-case.component';
import { SftpTestCaseService } from '../../../../../../main/webapp/app/entities/sftp-test-case/sftp-test-case.service';
import { SftpTestCase } from '../../../../../../main/webapp/app/entities/sftp-test-case/sftp-test-case.model';

describe('Component Tests', () => {

    describe('SftpTestCase Management Component', () => {
        let comp: SftpTestCaseComponent;
        let fixture: ComponentFixture<SftpTestCaseComponent>;
        let service: SftpTestCaseService;

        beforeEach(async(() => {
            TestBed.configureTestingModule({
                imports: [SftpbotTestModule],
                declarations: [SftpTestCaseComponent],
                providers: [
                    SftpTestCaseService
                ]
            })
            .overrideTemplate(SftpTestCaseComponent, '')
            .compileComponents();
        }));

        beforeEach(() => {
            fixture = TestBed.createComponent(SftpTestCaseComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(SftpTestCaseService);
        });

        describe('OnInit', () => {
            it('Should call load all on init', () => {
                // GIVEN
                const headers = new Headers();
                headers.append('link', 'link;link');
                spyOn(service, 'query').and.returnValue(Observable.of({
                    json: [new SftpTestCase(123)],
                    headers
                }));

                // WHEN
                comp.ngOnInit();

                // THEN
                expect(service.query).toHaveBeenCalled();
                expect(comp.sftpTestCases[0]).toEqual(jasmine.objectContaining({id: 123}));
            });
        });
    });

});
