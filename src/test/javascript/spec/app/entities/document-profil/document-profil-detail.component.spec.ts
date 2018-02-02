/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, async } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs/Observable';

import { CvthequeTestModule } from '../../../test.module';
import { DocumentProfilDetailComponent } from '../../../../../../main/webapp/app/entities/document-profil/document-profil-detail.component';
import { DocumentProfilService } from '../../../../../../main/webapp/app/entities/document-profil/document-profil.service';
import { DocumentProfil } from '../../../../../../main/webapp/app/entities/document-profil/document-profil.model';

describe('Component Tests', () => {

    describe('DocumentProfil Management Detail Component', () => {
        let comp: DocumentProfilDetailComponent;
        let fixture: ComponentFixture<DocumentProfilDetailComponent>;
        let service: DocumentProfilService;

        beforeEach(async(() => {
            TestBed.configureTestingModule({
                imports: [CvthequeTestModule],
                declarations: [DocumentProfilDetailComponent],
                providers: [
                    DocumentProfilService
                ]
            })
            .overrideTemplate(DocumentProfilDetailComponent, '')
            .compileComponents();
        }));

        beforeEach(() => {
            fixture = TestBed.createComponent(DocumentProfilDetailComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(DocumentProfilService);
        });

        describe('OnInit', () => {
            it('Should call load all on init', () => {
                // GIVEN

                spyOn(service, 'find').and.returnValue(Observable.of(new HttpResponse({
                    body: new DocumentProfil(123)
                })));

                // WHEN
                comp.ngOnInit();

                // THEN
                expect(service.find).toHaveBeenCalledWith(123);
                expect(comp.documentProfil).toEqual(jasmine.objectContaining({id: 123}));
            });
        });
    });

});
